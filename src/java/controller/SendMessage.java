
package controller;

import dal.ConversationDAO;
import dal.MessageDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.sql.SQLException;
import model.Conversation;
import model.Message;
import model.User;

public class SendMessage extends HttpServlet {

    private MessageDAO messageDAO = new MessageDAO();
    private ConversationDAO conversationDAO = new ConversationDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String conversationIdParam = request.getParameter("conversationId");

        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || (username == null && conversationIdParam == null)) {
            response.sendRedirect("error.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User recipient = null;

        if (username != null) {
            recipient = userDAO.getUserByUsernameM(username);
            if (recipient == null) {
                response.sendRedirect("error.jsp");
                return;
            }
        } else if (conversationIdParam != null) {
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

        Conversation conversation = conversationDAO.getConversationBetweenUsers(currentUser.getUsername(), recipient.getUsername());
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setConversationName("Chat between " + currentUser.getUsername() + " and " + recipient.getUsername());
            conversationDAO.createConversation(conversation, currentUser.getUsername(), recipient.getUsername());
        }

        List<Message> messages = messageDAO.getMessagesByConversationId(conversation.getConversationId());

        session.setAttribute("currentChatRecipient", recipient);
        session.setAttribute("currentChatMessages", messages);
        session.setAttribute("currentConversationId", conversation.getConversationId());
        session.setAttribute("conversations", conversationDAO.getAllConversationsForUser(currentUser.getUsername()));
        session.setAttribute("userConversation", conversationDAO.getAllUserConversationsForUser());
        session.setAttribute("userList2", userDAO.getAll());

        request.getRequestDispatcher("message.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        String messageContent = request.getParameter("message");
        String recipientUsername = request.getParameter("recipient");

        if (currentUser == null || messageContent == null || recipientUsername == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        Conversation conversation = conversationDAO.getConversationBetweenUsers(currentUser.getUsername(), recipientUsername);
        if (conversation == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        Message message = new Message();
        message.setConversationId(conversation.getConversationId());
        message.setSentBy(currentUser.getUsername());
        message.setSentAt(new Timestamp(System.currentTimeMillis()));
        message.setMsgContent(messageContent);
        message.setContentType("text");

        try {
            messageDAO.saveMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }

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

        response.sendRedirect("sendMessage?conversationId=" + conversation.getConversationId() + "&recipient=" + recipientUsername);
    }
}
