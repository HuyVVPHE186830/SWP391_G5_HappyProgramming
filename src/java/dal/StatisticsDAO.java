package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsDAO extends DBContext {

    // Total number of users by role
    public Map<String, Integer> getUserRolesStats() {
        Map<String, Integer> roleStats = new HashMap<>();
        String sql = "SELECT r.roleName, COUNT(u.id) AS count "
                + "FROM [User] u JOIN [Role] r ON u.roleId = r.roleId "
                + "GROUP BY r.roleName";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                roleStats.put(rs.getString("roleName"), rs.getInt("count"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return roleStats;
    }

    // Total number of courses and participants per course
    public Map<String, Integer> getCoursesStats() {
        Map<String, Integer> courseStats = new HashMap<>();
        String sql = "SELECT c.courseName, COUNT(p.username) AS participants "
                + "FROM Course c LEFT JOIN Participate p ON c.courseId = p.courseId "
                + "GROUP BY c.courseName";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                courseStats.put(rs.getString("courseName"), rs.getInt("participants"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return courseStats;
    }

    // Total number of blog posts per user
    public Map<String, Integer> getUserBlogStats() {
        Map<String, Integer> blogStats = new HashMap<>();
        String sql = "SELECT u.username, COUNT(b.blog_id) AS blogCount "
                + "FROM blogs b JOIN [User] u ON b.user_name = u.username "
                + "GROUP BY u.username";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                blogStats.put(rs.getString("username"), rs.getInt("blogCount"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return blogStats;
    }

    // Total messages per conversation
    public Map<String, Integer> getMessagesStats() {
        Map<String, Integer> messageStats = new HashMap<>();
        String sql = "SELECT c.conversationName, COUNT(m.msgContent) AS messageCount "
                + "FROM Conversation c LEFT JOIN Message m ON c.conversationId = m.conversationId "
                + "GROUP BY c.conversationName";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                messageStats.put(rs.getString("conversationName"), rs.getInt("messageCount"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return messageStats;
    }

    public Map<String, Integer> getUserBlogStats(String startDate, String endDate, String user) {
        Map<String, Integer> blogStats = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT u.username, COUNT(b.blog_id) AS blogCount "
                + "FROM blogs b JOIN [User] u ON b.user_name = u.username "
                + "WHERE 1=1 ");

        // Add conditions for start date, end date, and user if they are provided
        if (startDate != null && !startDate.isEmpty()) {
            sql.append("AND b.created_At >= ? ");
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append("AND b.created_At <= ? ");
        }
        if (user != null && !user.isEmpty()) {
            sql.append("AND u.username = ? ");
        }
        sql.append("GROUP BY u.username");

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (startDate != null && !startDate.isEmpty()) {
                st.setString(paramIndex++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                st.setString(paramIndex++, endDate);
            }
            if (user != null && !user.isEmpty()) {
                st.setString(paramIndex, user);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                blogStats.put(rs.getString("username"), rs.getInt("blogCount"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return blogStats;
    }

    public List<String> getAllUsernames() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT username FROM [User]";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return users;
    }

    public Map<String, Integer> getRequestStats(String createdDate, String username, Integer status) {
        Map<String, Integer> requestStats = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT requestStatus, COUNT(*) AS count FROM Request WHERE 1=1 ");

        // Dynamically add filters
        if (createdDate != null && !createdDate.isEmpty()) {
            sql.append("AND CAST(requestTime AS DATE) = ? ");
        }
        if (username != null && !username.isEmpty()) {
            sql.append("AND username = ? ");
        }
        if (status != null) {
            sql.append("AND requestStatus = ? ");
        }
        sql.append("GROUP BY requestStatus");

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (createdDate != null && !createdDate.isEmpty()) {
                st.setString(paramIndex++, createdDate);
            }
            if (username != null && !username.isEmpty()) {
                st.setString(paramIndex++, username);
            }
            if (status != null) {
                st.setInt(paramIndex, status);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                requestStats.put(rs.getString("requestStatus"), rs.getInt("count"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return requestStats;
    }

}
