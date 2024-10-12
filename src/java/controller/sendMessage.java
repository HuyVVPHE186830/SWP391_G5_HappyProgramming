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
import model.User;
import model.UserConversation;


public class sendMessage extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");

        // Lấy người dùng hiện tại
                User currentUser = (User) session.getAttribute("user");

        if (username == null || currentUser == null) {
            response.sendRedirect("error.jsp");
            return;
        }
UserDAO userDAO = new UserDAO();
        // Lấy thông tin recipient
        User recipient = userDAO.getUserByUsernameM(username);
        if (recipient == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        // Tìm conversation giữa currentUser và recipient
        ConversationDAO conversationDAO = new ConversationDAO();
        Conversation conversation = conversationDAO.getConversationBetweenUsers(currentUser.getUsername(), username);
        
        // Nếu conversation không tồn tại, tạo một cuộc hội thoại mới
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setConversationName("Conversation between " + currentUser.getUsername() + " and " + username);
            // Thêm thêm thông tin về conversation
            conversationDAO.createConversation(conversation, currentUser.getUsername(), username);
        }

        // Lấy danh sách tin nhắn
        List<Message> messages = MessageDAO.getMessagesByConversationId(conversation.getConversationId());

        // Lưu thông tin vào session
        session.setAttribute("currentChatRecipient", recipient);
        session.setAttribute("currentChatMessages", messages);
        session.setAttribute("currentConversationId", conversation.getConversationId());
        session.setAttribute("conversations", conversationDAO.getAllConversationsForUser(currentUser.getUsername()));

        // Chuyển hướng đến trang chat
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messageContent = request.getParameter("message");
        String recipientUsername = request.getParameter("recipient");
        int conversationId = Integer.parseInt(request.getParameter("conversationId"));

        // Lưu tin nhắn vào cơ sở dữ liệu
        Message message = new Message();
        message.setConversationId(conversationId);
        message.setSentBy((String) request.getSession().getAttribute("currentUser.username"));
        message.setSentAt(new Timestamp(System.currentTimeMillis())); 
        message.setMsgContent(messageContent);
        message.setContentType("text");

        // Gọi hàm để lưu vào DB
        MessageDAO messageDAO = new MessageDAO();
        messageDAO.saveMessage(message);

        // Redirect về trang chat
        response.sendRedirect("message?conversationId=" + conversationId);
    }
}
