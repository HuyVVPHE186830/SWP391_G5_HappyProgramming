package controller;

import dal.ConversationDAO;
import dal.MessageDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.sql.Timestamp;
import model.Conversation;
import model.Message;
import java.sql.SQLException;
import model.User;

public class sendMessage extends HttpServlet {

    MessageDAO messageDAO = new MessageDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String conversationIdParam = request.getParameter("conversationId");

        // Lấy người dùng hiện tại
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || (username == null && conversationIdParam == null)) {
            response.sendRedirect("error.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User recipient = null;

        if (username != null) {
            // Nếu có username, lấy recipient từ username
            recipient = userDAO.getUserByUsernameM(username);
            if (recipient == null) {
                response.sendRedirect("error.jsp");
                return;
            }
        } else if (conversationIdParam != null) {
            // Nếu có conversationId, lấy recipient từ cuộc hội thoại
            int conversationId;
            try {
                conversationId = Integer.parseInt(conversationIdParam);
                if (conversationId <= 0) {
                    throw new NumberFormatException("Invalid conversation ID");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp");
                return;
            }

            // Lấy conversation và recipient từ conversationId
            ConversationDAO conversationDAO = new ConversationDAO();
            Conversation conversation = conversationDAO.getConversationById(conversationId);
            if (conversation != null) {
                List<String> participantUsernames = conversationDAO.getParticipantsByConversationId(conversationId);
                for (String participantUsername : participantUsernames) {
                    if (!participantUsername.equals(currentUser.getUsername())) {
                        recipient = userDAO.getUserByUsernameM(participantUsername);
                        break;
                    }
                }
            }
        }

        if (recipient == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        // Kiểm tra và tạo cuộc hội thoại nếu chưa tồn tại
        ConversationDAO conversationDAO = new ConversationDAO();
        Conversation conversation = conversationDAO.getConversationBetweenUsers(currentUser.getUsername(), recipient.getUsername());
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setConversationName("Chat between " + currentUser.getUsername() + " and " + recipient.getUsername());
            conversationDAO.createConversation(conversation, currentUser.getUsername(), recipient.getUsername());
        }

        // Lấy danh sách tin nhắn
        List<Message> messages = messageDAO.getMessagesByConversationId(conversation.getConversationId());

        // Lưu thông tin vào session
        session.setAttribute("currentChatRecipient", recipient);
        session.setAttribute("currentChatMessages", messages);
        session.setAttribute("currentConversationId", conversation.getConversationId());
        session.setAttribute("conversations", conversationDAO.getAllConversationsForUser(currentUser.getUsername()));

        // Chuyển hướng đến trang chat
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // Lấy các tham số từ request
        String messageContent = request.getParameter("message");
        String recipientUsername = request.getParameter("recipient");

        // Kiểm tra các tham số
        if (currentUser == null || messageContent == null || recipientUsername == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        // Kiểm tra và lấy cuộc hội thoại
        ConversationDAO conversationDAO = new ConversationDAO();
        Conversation conversation = conversationDAO.getConversationBetweenUsers(currentUser.getUsername(), recipientUsername);

        // Tạo đối tượng Message
        Message message = new Message();
        message.setConversationId(conversation.getConversationId());
        message.setSentBy(currentUser.getUsername());
        message.setSentAt(new Timestamp(System.currentTimeMillis()));
        message.setMsgContent(messageContent);
        message.setContentType("text");

        // Lưu tin nhắn vào cơ sở dữ liệu
        try {
            messageDAO.saveMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }

        // Cập nhật thông tin recipient và messages vào session
        UserDAO userDAO = new UserDAO();
        User recipient = userDAO.getUserByUsernameM(recipientUsername);
        if (recipient == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        session.setAttribute("currentChatRecipient", recipient);
        List<Message> messages = messageDAO.getMessagesByConversationId(conversation.getConversationId());
        session.setAttribute("currentChatMessages", messages);
        session.setAttribute("currentConversationId", conversation.getConversationId());

        // Redirect về trang chat với cùng các tham số
        response.sendRedirect("sendMessage?conversationId=" + conversation.getConversationId() + "&recipient=" + recipientUsername);
    }
}
