/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Rating;
import java.sql.ResultSet;
import java.util.Date;
import model.Course;
import model.User;

/**
 *
 * @author yeuda
 */
public class RatingDAO extends DBContext {

    public void addFeedback(String CurrentUser, String RatedUser, int Star, int CourseID, String RatedContent) {

        String sql = "INSERT INTO [dbo].[Rating]\n"
                + "           ([ratedFromUser]\n"
                + "           ,[ratedToUser]\n"
                + "           ,[noStar]\n"
                + "           ,[courseId]\n"
                + "           ,[ratingComment])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, CurrentUser);
            statement.setString(2, RatedUser);
            statement.setInt(3, Star);
            statement.setInt(4, CourseID);
            statement.setString(5, RatedContent);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        RatingDAO dao = new RatingDAO();
//        int o = dao.getTurnStar(3, 29);
//        System.out.println(o);
        List<User> ratings = new ArrayList<>();
        ratings = dao.getListRatedFromToId(29);
        for (User rating : ratings) {
            System.out.println(rating.getCourseName());
        }
//        dao.addFeedback("ducmentor", "anmentor", 4, 1, "non2");
    }

    public List<Rating> getAll() {
        List<Rating> ratings = new ArrayList<>();
        String query = "SELECT * FROM [Rating]";

        try (PreparedStatement pstmt = connection.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String ratedFromUser = rs.getString("ratedFromUser");
                String ratedToUser = rs.getString("ratedToUser");
                int noStar = rs.getInt("noStar");
                int courseId = rs.getInt("courseId");
                String ratingComment = rs.getString("ratingComment");

                Rating rating = new Rating(ratedFromUser, ratedToUser, noStar, courseId, ratingComment);
                ratings.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return ratings;
    }

    public int getByUsnIdAndCId(float ratedToUser, int courseID) {
        int rating = 0; // Mặc định là 0 nếu không tìm thấy đánh giá
        String query = "SELECT \n"
                + "    CAST(AVG(CAST(r.noStar AS DECIMAL(10, 1))) AS DECIMAL(10, 1)) AS averageRating\n"
                + "FROM \n"
                + "    [Rating] r\n"
                + "JOIN \n"
                + "    [Participate] p ON r.courseId = p.courseId \n"
                + "JOIN \n"
                + "    [User] u ON p.username = u.username \n"
                + "WHERE \n"
                + "    r.ratedToUser = p.username AND \n"
                + "    p.username = u.username AND \n"
                + "    r.courseId = ? AND \n"
                + "    u.id = ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, courseID);
            pstmt.setFloat(2, ratedToUser);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rating = rs.getInt("averageRating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return rating;
    }

    public List<Integer> getDistinctNoStars() {
        List<Integer> noStars = new ArrayList<>();
        String sql = "SELECT DISTINCT noStar FROM Rating ORDER BY noStar";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                noStars.add(resultSet.getInt("noStar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return noStars;
    }

    public List<Rating> getRateByUserId(int ratedID) {
        List<Rating> ratings = new ArrayList<>();
        String query = "SELECT * FROM [Rating] WHERE ratedToUser = (SELECT username FROM [User] WHERE id = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ratedID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String ratedFromUser = rs.getString("ratedFromUser");
                    String ratedToUser = rs.getString("ratedToUser");
                    int noStar = rs.getInt("noStar");
                    int courseId = rs.getInt("courseId");
                    String ratingComment = rs.getString("ratingComment");

                    Rating rating = new Rating(ratedFromUser, ratedToUser, noStar, courseId, ratingComment);
                    ratings.add(rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratings;
    }

    public List<Rating> getRateByNoStar2(int numS, String ratedUsername) {
        List<Rating> ratings = new ArrayList<>();
        String query = "SELECT * FROM [Rating] WHERE noStar = ? AND ratedToUser = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, numS);
            pstmt.setString(2, ratedUsername);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String ratedFromUser = rs.getString("ratedFromUser");
                    String ratedToUser = rs.getString("ratedToUser");
                    int noStar = rs.getInt("noStar");
                    int courseId = rs.getInt("courseId");
                    String ratingComment = rs.getString("ratingComment");

                    Rating rating = new Rating(ratedFromUser, ratedToUser, noStar, courseId, ratingComment);
                    ratings.add(rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return ratings;
    }

    public float getAverageStar(int ratedId) {
        float averageRating = 0.0f;
        String query = "SELECT CAST(AVG(noStar) AS DECIMAL(10, 2)) AS averageRating "
                + "FROM [Rating] "
                + "WHERE ratedToUser = (SELECT username FROM [User] WHERE id = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ratedId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    averageRating = rs.getFloat("averageRating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return averageRating;
    }

    public int getRankMentor(int userID) {
        int rank = 0;
        String query = "SELECT COUNT(*) + 1 AS rank "
                + "FROM ( "
                + "    SELECT ratedToUser, AVG(noStar) AS averageRating "
                + "    FROM [Rating] "
                + "    GROUP BY ratedToUser "
                + ") AS ratings "
                + "WHERE averageRating > ( "
                + "    SELECT AVG(noStar) "
                + "    FROM [Rating] "
                + "    WHERE ratedToUser = (SELECT username FROM [User] WHERE id = ?) "
                + ")";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rank = rs.getInt("rank");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return rank;
    }

    public List<Course> getCourseOfRated(int id) {
        List<Course> list = new ArrayList<>();
        String query = "SELECT * FROM Course c \n"
                + "	JOIN Participate p ON c.courseId = p.courseId\n"
                + "	WHERE p.username = (SELECT username FROM [User] WHERE id = ?)\n"
                + "	AND p.statusId = 1";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int CourseID = rs.getInt("courseId");
                    String name = rs.getString("courseName");
                    String description = rs.getString("courseDescription");
                    Course c = new Course(CourseID, name, description);
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return list;
    }

    public List<Rating> getRateByCourse(int courseID, int ratedID4) {
        List<Rating> ratings = new ArrayList<>();
        String query = "SELECT * FROM Rating\n"
                + "	WHERE courseId = ? "
                + "AND ratedToUser = (SELECT username FROM [User] WHERE id = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, courseID);
            pstmt.setInt(2, ratedID4);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String ratedFromUser = rs.getString("ratedFromUser");
                    String ratedToUser = rs.getString("ratedToUser");
                    int noStar = rs.getInt("noStar");
                    int courseId = rs.getInt("courseId");
                    String ratingComment = rs.getString("ratingComment");

                    Rating rating = new Rating(ratedFromUser, ratedToUser, noStar, courseId, ratingComment);
                    ratings.add(rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return ratings;
    }

    public int getTurnStar(int i, int userID) {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM [Rating] WHERE noStar = ?\n"
                + "AND ratedToUser =(SELECT username FROM [User] WHERE id = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, i);
            pstmt.setInt(2, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return count;
    }

    public int getTurnStarOverallByUserId(int userID) {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM [Rating] WHERE\n"
                + "ratedToUser =(SELECT username FROM [User] WHERE id = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return count;
    }

    public void addFeedbackById(int ratedFromID, int ratedID, int noS, int courssE, String comment) {
        String sql = "INSERT INTO [Rating] (ratedFromUser, ratedToUser, noStar, courseId, ratingComment) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            String ratedFromUser = getUsernameById(ratedFromID);
            String ratedToUser = getUsernameById(ratedID);

            statement.setString(1, ratedFromUser);
            statement.setString(2, ratedToUser);
            statement.setInt(3, noS);
            statement.setInt(4, courssE);
            statement.setString(5, comment);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCourseByBoth(int ratedID, int ratedFromID) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Course> getCourseByRated(int ratedID) {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM [Course] c\n"
                + "JOIN [Participate] p ON c.courseId = p.courseId\n"
                + "WHERE p.username = (SELECT username FROM [User] u WHERE u.id = ?) AND p.statusId = 1";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, ratedID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int CourseID = rs.getInt("courseId");
                    String name = rs.getString("courseName");
                    String description = rs.getString("courseDescription");
                    Course course = new Course(CourseID, name, description);
                    list.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private String getUsernameById(int userId) {
        String username = null;
        String query = "SELECT username FROM [User] WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return username;
    }

    public List<Rating> getRateByNoStar3(int numS, int ratedID) {
        List<Rating> ratings = new ArrayList<>();
        String query = "SELECT * FROM [Rating] WHERE noStar = ? AND ratedToUser = (SELECT username FROM [User] WHERE id = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, numS); // Set the noStar value
            pstmt.setInt(2, ratedID); // Set the ratedID to find the username

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String ratedFromUser = rs.getString("ratedFromUser");
                    String ratedToUser = rs.getString("ratedToUser");
                    int noStar = rs.getInt("noStar");
                    int courseId = rs.getInt("courseId");
                    String ratingComment = rs.getString("ratingComment");

                    // Create a Rating object and add it to the list
                    Rating rating = new Rating(ratedFromUser, ratedToUser, noStar, courseId, ratingComment);
                    ratings.add(rating);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return ratings;
    }

    public List<User> getListRatedFromToId(int ratedID) {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.*, c.courseName "
                + "FROM Rating r "
                + "JOIN [User] u ON r.ratedFromUser = u.username "
                + "JOIN Course c ON r.courseId = c.courseId "
                + "WHERE r.ratedToUser = (SELECT username FROM [User] WHERE id = ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ratedID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    Date dob = rs.getDate("dob");
                    String mail = rs.getString("mail");
                    Date createdDate = rs.getDate("createdDate");
                    String avatarPath = rs.getString("avatarPath");
                    String cvPath = rs.getString("cvPath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    boolean isVerified = rs.getBoolean("isVerified");
                    String verificationCode = rs.getString("verification_code");
                    int roleId = rs.getInt("roleId");
                    String courseName = rs.getString("courseName");
                    User u = new User(id, username, password, firstName,
                            lastName, dob, mail, createdDate, avatarPath,
                            cvPath, activeStatus, isVerified, verificationCode,
                            roleId, courseName);
                    users.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return users;
    }

    
}
