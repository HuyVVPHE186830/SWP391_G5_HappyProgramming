/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Rating;
import java.sql.ResultSet;

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
//        int o = dao.getByUsnAndCId("anmentor", 2);
//        System.out.println(o);
//        List<Rating> ratings = new ArrayList<>();
//        ratings = dao.getAll();
//        for (Rating rating : ratings) {
//            System.out.println(rating);
//        }
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

}