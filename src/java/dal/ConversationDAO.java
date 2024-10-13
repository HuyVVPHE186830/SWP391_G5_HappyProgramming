/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Conversation;
import model.Course;
import model.User;
import model.UserConversation;

/**
 *
 * @author yeuda
 */
public class ConversationDAO extends DBContext {

    public Conversation getConversationById(int conversationId) {
        String query = "SELECT * FROM Conversation WHERE conversationId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, conversationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Conversation conversation = new Conversation();
                conversation.setConversationId(rs.getInt("conversationId"));
                conversation.setConversationName(rs.getString("conversationName"));
                return conversation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<UserConversation> getUsersInConversation(int conversationId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Conversation> getAllConversationsForUser(String username) {
        List<Conversation> conversations = new ArrayList<>();
        String query = "SELECT c.conversationId, c.conversationName "
                + "FROM Conversation c "
                + "JOIN User_Conversation uc ON c.conversationId = uc.conversationId "
                + "WHERE uc.username = ?";

        try (
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Conversation conversation = new Conversation();
                    conversation.setConversationId(rs.getInt("conversationId"));
                    conversation.setConversationName(rs.getString("conversationName"));
                    conversations.add(conversation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return conversations; // Trả về danh sách cuộc hội thoại
    }

    public Conversation getConversationBetweenUsers(String username, String username0) {
        Conversation conversation = null;
        String query = "SELECT TOP 1 c.conversationId, c.conversationName "
                + "FROM Conversation c "
                + "JOIN User_Conversation uc1 ON c.conversationId = uc1.conversationId "
                + "JOIN User_Conversation uc2 ON c.conversationId = uc2.conversationId "
                + "WHERE uc1.username = ? AND uc2.username = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username0);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    conversation = new Conversation();
                    conversation.setConversationId(rs.getInt("conversationId"));
                    conversation.setConversationName(rs.getString("conversationName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversation;
    }

    public void createConversation(Conversation conversation, String username, String username0) {
        String conversationQuery = "INSERT INTO Conversation (conversationName) VALUES (?)";
        String userConversationQuery = "INSERT INTO User_Conversation (conversationId, username) VALUES (?, ?)";

        try (
                PreparedStatement conversationStmt = connection.prepareStatement(conversationQuery, Statement.RETURN_GENERATED_KEYS)) {

            // Thêm thông tin vào bảng Conversation
            conversationStmt.setString(1, conversation.getConversationName());
            int affectedRows = conversationStmt.executeUpdate();

            // Nếu có bản ghi được thêm, lấy conversationId
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = conversationStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int conversationId = generatedKeys.getInt(1);
                        conversation.setConversationId(conversationId); // Cập nhật ID vào đối tượng conversation

                        // Thêm người dùng vào bảng User_Conversation
                        try (PreparedStatement userConversationStmt = connection.prepareStatement(userConversationQuery)) {
                            userConversationStmt.setInt(1, conversationId);
                            userConversationStmt.setString(2, username);
                            userConversationStmt.executeUpdate();

                            userConversationStmt.setString(2, username0);
                            userConversationStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getParticipantsByConversationId(int conversationId) {
        List<String> participants = new ArrayList<>();
        String query = "SELECT username FROM User_Conversation WHERE conversationId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, conversationId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                participants.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

}
