package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatisticsDAO extends DBContext {

    // Total number of users by role
    public Map<String, Integer> getUserRolesStats() {
        Map<String, Integer> roleStats = new HashMap<>();
        String sql = "SELECT r.roleName, COUNT(u.id) AS count " +
                     "FROM [User] u JOIN [Role] r ON u.roleId = r.roleId " +
                     "GROUP BY r.roleName";

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
        String sql = "SELECT c.courseName, COUNT(p.username) AS participants " +
                     "FROM Course c LEFT JOIN Participate p ON c.courseId = p.courseId " +
                     "GROUP BY c.courseName";

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
        String sql = "SELECT u.username, COUNT(b.blog_id) AS blogCount " +
                     "FROM blogs b JOIN [User] u ON b.user_name = u.username " +
                     "GROUP BY u.username";

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
        String sql = "SELECT c.conversationName, COUNT(m.msgContent) AS messageCount " +
                     "FROM Conversation c LEFT JOIN Message m ON c.conversationId = m.conversationId " +
                     "GROUP BY c.conversationName";

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
}
