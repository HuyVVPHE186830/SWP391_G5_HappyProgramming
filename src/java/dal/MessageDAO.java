/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;
import model.Message;
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
 * @author yeuda
 */
public class MessageDAO extends DBContext {
    
    public List<Message> getMessagesByConversationId(int conversationId) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM Message WHERE conversationId = ? ORDER BY sentAt";

        try {

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, conversationId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Message message = new Message();
                    message.setConversationId(rs.getInt("conversationId"));
                    message.setSentBy(rs.getString("sentBy"));
                    message.setSentAt(rs.getTimestamp("sentAt"));
                    message.setMsgContent(rs.getString("msgContent"));
                    message.setContentType(rs.getString("contentType"));
                    message.setMessageId(rs.getInt("messageId"));

                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public void saveMessage(Message message) throws SQLException{
        String query = "INSERT INTO Message (conversationId, sentBy, sentAt, msgContent, contentType) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, message.getConversationId());
            stmt.setString(2, message.getSentBy());
            stmt.setTimestamp(3, message.getSentAt());
            stmt.setString(4, message.getMsgContent());
            stmt.setString(5, message.getContentType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

}
