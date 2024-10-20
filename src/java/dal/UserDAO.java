/*
                 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
                 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Course;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDAO extends DBContext {

    public User getUserByUsernameM(String username) {
        String sql = "SELECT * FROM [User] WHERE username=?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) { // Chỉ cần kiểm tra nếu có kết quả
                int id = rs.getInt("id");
                String usernamE = rs.getString("username");
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

                return new User(id, usernamE, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null; 
    }

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
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
                User u = new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }

    public int countUser(int roleId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM [User] where roleId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return count;
    }

    public List<User> getAllUserByRoleId(int roleId) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE roleId = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, roleId);
            ResultSet rs = pre.executeQuery();

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
                list.add(new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<User> getAllMentorByCourseId(int courseId) {
        List<User> list = new ArrayList<>();
        String sql = "select [User].* from [User] join Participate on Participate.username = [User].username\n"
                + "join Course on Course.courseId = Participate.courseId\n"
                + "where [User].roleId = 2 and course.courseId = ? and Participate.ParticipateRole = 2\n"
                + "order by [User].id";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, courseId);
            ResultSet rs = pre.executeQuery();

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
                list.add(new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<User> getAllMentorBySearchKey(int courseId, String searchKey) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT [User].* FROM [User] "
                + "JOIN Participate ON Participate.username = [User].username "
                + "JOIN Course ON Course.courseId = Participate.courseId "
                + "WHERE [User].roleId = 2 AND Course.courseId = ? and Participate.ParticipateRole = 2 "
                + "AND ([User].firstName COLLATE Latin1_General_CI_AI LIKE ? "
                + "OR [User].lastName COLLATE Latin1_General_CI_AI LIKE ?) "
                + "ORDER BY [User].id";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, courseId);
            pre.setString(2, "%" + searchKey + "%");
            pre.setString(3, "%" + searchKey + "%");
            ResultSet rs = pre.executeQuery();

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
                list.add(new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<User> getAllMentorByCourseIdOrderByName(int courseId) {
        List<User> list = new ArrayList<>();
        String sql = "select [User].* from [User] join Participate on Participate.username = [User].username\n"
                + "join Course on Course.courseId = Participate.courseId\n"
                + "where [User].roleId = 2 and Course.courseId = ? and Participate.ParticipateRole = 2\n"
                + "ORDER BY [User].firstName";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, courseId);
            ResultSet rs = pre.executeQuery();

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
                list.add(new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<User> getAllMentorByCourseIdExceptOne(int courseId, String username) {
        List<User> list = new ArrayList<>();
        String sql = "select [User].* from [User] join Participate on Participate.username = [User].username\n"
                + "join Course on Course.courseId = Participate.courseId\n"
                + "where [User].roleId = 2 and Course.courseId = ? and Participate.ParticipateRole = 2\n"
                + "and [User].username <> ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, courseId);
            pre.setString(2, username); // Đặt giá trị của username vào câu truy vấn
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fetchedUsername = rs.getString("username");
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
                list.add(new User(id, fetchedUsername, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public boolean checkPassword(int id, String pass) {
        boolean f = false;
        try {
            String sql = "select * from [User] where id = ? and password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                f = true;
            }
        } catch (Exception e) {
        }
        return f;
    }

    public boolean changePass(User u) {
        boolean f = false;
        try {
            String sql = "update [User] set password = ? where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, u.getPassword());
            ps.setInt(2, u.getId());

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public User getUserById(int id) {
        User u = null;
        try {
            String sql = "select * from [User] where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
                u = new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public boolean resetPassWord(String veriCode, String newPass) {
        boolean f = false;
        try {
            String sql = "update [User] set password = ? where [password] = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newPass);
            ps.setString(2, veriCode);

            int i = ps.executeUpdate();
            if (i == 1) {
                f = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public List<Course> getCourseByQuantityEnroll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT c.courseId, c.courseName, \n"
                + "       CAST(c.courseDescription AS NVARCHAR(MAX)) AS courseDescription, \n"
                + "       c.createdAt, \n"
                + "       COUNT(p.username) AS user_count\n"
                + "FROM Course c\n"
                + "JOIN Participate p ON c.courseId = p.courseId\n"
                + "JOIN [User] u ON p.username = u.username\n"
                + "JOIN Status s ON p.statusId = s.statusId\n"
                + "WHERE p.participateRole = 3 AND p.statusId = 1\n"
                + "GROUP BY c.courseId, c.courseName, \n"
                + "         CAST(c.courseDescription AS NVARCHAR(MAX)), \n"
                + "         c.createdAt\n"
                + "ORDER BY user_count DESC;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("courseId");
                String name = rs.getString("courseName");
                String des = rs.getString("courseDescription");
                Date date = rs.getDate("createdAt");
                int count = rs.getInt("user_count");
                Course e = new Course(id, name, des, date);
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void registerUser(User user) {
        String sql = "INSERT INTO [User] (username, password, firstName, lastName, dob, mail, createdDate, avatarPath, CVPath, activeStatus,isVerified, roleId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setDate(5, new java.sql.Date(user.getDob().getTime()));
            statement.setString(6, user.getMail());
            statement.setDate(7, new java.sql.Date(user.getCreatedDate().getTime()));
            statement.setString(8, user.getAvatarPath());
            statement.setString(9, user.getCvPath());
            statement.setBoolean(10, user.isActiveStatus());
            statement.setBoolean(11, user.isIsVerified());
            statement.setInt(12, user.getRoleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByMail(String mail) {
        User u = null;
        try {
            String sql = "select * from [User] where mail = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mail);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Date dob = rs.getDate("dob");
                Date createdDate = rs.getDate("createdDate");
                String avatarPath = rs.getString("avatarPath");
                String cvPath = rs.getString("cvPath");
                boolean activeStatus = rs.getBoolean("activeStatus");
                boolean isVerified = rs.getBoolean("isVerified");
                String verificationCode = rs.getString("verification_code");
                int roleId = rs.getInt("roleId");
                u = new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }

    public void updateProfile(String username, User user) {
        String sql = "UPDATE [User] SET firstName = ?, lastName = ?, dob = ?, mail = ?, CVPath = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new java.sql.Date(user.getDob().getTime()));
            statement.setString(4, user.getMail());
            statement.setString(5, user.getCvPath());
            statement.setString(6, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserAvatarPath(String username, String avatarPath) {
        String sql = "UPDATE [User] SET avatarPath = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, avatarPath);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserNameDuplicate(String username) {
        String sql = "SELECT * FROM [dbo].[User] WHERE username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username); // Set the username parameter
            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertUser(String userName, String password, String firstName, String lastName,
            Date dob, String email, Date createdDate,
            String avatarPath, String cvPath, boolean activeStatus,
            boolean isVerified, String verificationCode, int roleId) {
        String sql = "INSERT INTO [dbo].[User] (username, password, firstName, lastName, dob, mail, createdDate, avatarPath, CVPath, activeStatus, isVerified, verification_code, roleId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userName);  // Username
            st.setString(2, password);   // Password
            st.setString(3, firstName);  // First Name
            st.setString(4, lastName);   // Last Name
            st.setDate(5, new java.sql.Date(dob.getTime()));  // Date of Birth
            st.setString(6, email);      // Email
            st.setDate(7, new java.sql.Date(createdDate.getTime()));  // Created Date
            st.setString(8, avatarPath);  // Avatar Path
            st.setString(9, cvPath);      // CV Path
            st.setBoolean(10, activeStatus); // Active Status
            st.setBoolean(11, isVerified);   // Is Verified
            st.setString(12, verificationCode); // Verification Code
            st.setInt(13, roleId); // Role ID

            // Execute the insert
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // Consider logging the error for better tracking
        }
    }

    public boolean updateUserInfo(String username, String firstName, String lastName, Date dob, String email, String avatarPath, String cvPath, boolean activeStatus, boolean isVerified, int roleId) {
        String sql = "UPDATE [user] SET firstName = ?, lastName = ?, dob = ?, mail = ?, avatarPath = ?, cvPath = ?, activeStatus = ?, isVerified = ?, roleId = ? WHERE username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setDate(3, new java.sql.Date(dob.getTime())); // Convert java.util.Date to java.sql.Date
            st.setString(4, email);
            st.setString(5, avatarPath);
            st.setString(6, cvPath);
            st.setBoolean(7, activeStatus);
            st.setBoolean(8, isVerified);
            st.setInt(9, roleId);
            st.setString(10, username);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception as needed
        }
    }

    public boolean deactivateUser(String username) {
        String sql = "UPDATE [user] SET activeStatus = 0 WHERE username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception as needed
        }
    }

    public boolean checkUserDeactivated(String username) {
        String sql = "SELECT * FROM [dbo].[User] WHERE username = ? and activeStatus = 0";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username); // Set the username parameter
            ResultSet rs = st.executeQuery();
            //co ket qua thi return true (da deactivate)
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getUsersBySearchName(String txt) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[User] WHERE [firstName] LIKE ? or [lastName] LIKE ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + txt + "%");
            st.setString(2, "%" + txt + "%");
            ResultSet rs = st.executeQuery();
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
                User u = new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> getUsersByUsername(String txt) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[User] WHERE [username] = ? ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, txt);
            ResultSet rs = st.executeQuery();
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
                User u = new User(id, username, password, firstName, lastName, dob, mail, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void verifyEmail(String username, User user) {
        String sql = "UPDATE [User] SET isVerified = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, user.isIsVerified());
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newPassWord(String username) {

        String sql = "DECLARE @NewCode VARCHAR(6);\n"
                + "DECLARE @Username NVARCHAR(50) = 'your_username'; \n"
                + "SET @NewCode = RIGHT(CONVERT(VARCHAR(10), CAST(NEWID() AS BINARY(4)) % 1000000), 6);\n"
                + "WHILE EXISTS (SELECT 1 FROM [User] WHERE password = @NewCode)\n"
                + "BEGIN\n"
                + "    SET @NewCode = RIGHT(CONVERT(VARCHAR(10), CAST(NEWID() AS BINARY(4)) % 1000000), 6);\n"
                + "END\n"
                + "UPDATE [User]\n"
                + "SET password = @NewCode\n"
                + "WHERE username = ?;  ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String newPassWord2(String username) {
        String newCode = null;

        // Generate new password in a separate query
        String generatePasswordSql
                = "DECLARE @NewCode VARCHAR(6); "
                + "SET @NewCode = RIGHT(CONVERT(VARCHAR(10), CAST(NEWID() AS BINARY(4)) % 1000000), 6); "
                + "WHILE EXISTS (SELECT 1 FROM [User] WHERE password = @NewCode) "
                + "BEGIN "
                + "    SET @NewCode = RIGHT(CONVERT(VARCHAR(10), CAST(NEWID() AS BINARY(4)) % 1000000), 6); "
                + "END; "
                + "SELECT @NewCode AS NewPassword;";

        // Update password
        String updatePasswordSql = "UPDATE [User] SET password = ? WHERE username = ?;";

        try {
            // Step 1: Generate new password
            try (PreparedStatement generateStmt = connection.prepareStatement(generatePasswordSql); ResultSet resultSet = generateStmt.executeQuery()) {
                if (resultSet.next()) {
                    newCode = resultSet.getString("NewPassword");
                }
            }

            // Step 2: Update password in the database
            if (newCode != null) {
                try (PreparedStatement updateStmt = connection.prepareStatement(updatePasswordSql)) {
                    updateStmt.setString(1, newCode);
                    updateStmt.setString(2, username);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newCode;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        User user = dao.getUserById(31);
        List<User> list = dao.getAllMentorBySearchKey(1, "h");
        for(User l : list) {
            System.out.println(l);
        }
        if(user != null){
            System.out.println(user);
        }else{
            System.out.println("Not found");
        }
      
    }
}
