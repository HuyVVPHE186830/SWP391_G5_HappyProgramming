package controller;

import dal.ConversationDAO;
import dal.CourseDAO;
import dal.MessageDAO;
import dal.ParticipateDAO;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import model.Conversation;
import model.Course;
import model.Message;
import model.Participate;
import model.User;

public class SendMessage extends HttpServlet {
    private MessageDAO messageDAO = new MessageDAO();
    private UserDAO uDAO = new UserDAO();
    private ConversationDAO conversationDAO = new ConversationDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private ParticipateDAO pDAO = new ParticipateDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("homementor.jsp");
            return;
        }

        String username = request.getParameter("username");
        String conversationIdParam = request.getParameter("conversationId");

        // Lấy danh sách khóa học và người tham gia
        List<Course> listCourse4 = courseDAO.getAll();
        List<User> listUser4 = uDAO.getAllUserByRoleId(2);
        List<Participate> listParticipate4 = pDAO.getAll();
        User oldestAdmin = uDAO.getOldestAdmin();

        request.getSession().setAttribute("oldestAdmin", oldestAdmin);
        request.getSession().setAttribute("listUser4", listUser4);
        request.getSession().setAttribute("listParticipate4", listParticipate4);
        request.getSession().setAttribute("listCourse4", listCourse4);

        Conversation latestConversation = conversationDAO.getLatestConversationWithMessageForUser(currentUser.getUsername());
        int lastConversationId = (latestConversation != null) ? latestConversation.getConversationId() : -1; // Sử dụng -1 nếu không có

        request.getSession().setAttribute("lastConversationId", lastConversationId);

        User recipient = null;

        if (username != null) {
            recipient = uDAO.getUserByUsernameM(username);
            if (recipient == null) {
                response.sendRedirect("homementor.jsp");
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
                response.sendRedirect("homementor.jsp");
                return;
            }

            Conversation conversation = conversationDAO.getConversationById(conversationId);
            if (conversation != null) {
                List<String> participantUsernames = conversationDAO.getParticipantsByConversationId(conversationId);
                for (String participantUsername : participantUsernames) {
                    if (!participantUsername.equals(currentUser.getUsername())) {
                        recipient = uDAO.getUserByUsernameM(participantUsername);
                        break;
                    }
                }
            }
        }

        if (recipient == null) {
            response.sendRedirect("homementor.jsp");
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
        session.setAttribute("userList2", uDAO.getAll());

        request.getRequestDispatcher("message.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("homementor.jsp");
            return;
        }

        String messageContent = request.getParameter("message");
        String recipientUsername = request.getParameter("recipient");

        if (messageContent == null || recipientUsername == null) {
            response.sendRedirect("homementor.jsp");
            return;
        }

        Conversation conversation = conversationDAO.getConversationBetweenUsers(currentUser.getUsername(), recipientUsername);
        if (conversation == null) {
            response.sendRedirect("homementor.jsp");
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
            response.sendRedirect("homementor.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User recipient = userDAO.getUserByUsernameM(recipientUsername);
        if (recipient == null) {
            response.sendRedirect("homementor.jsp");
            return;
        }

        session.setAttribute("currentChatRecipient", recipient);
        List<Message> messages = messageDAO.getMessagesByConversationId(conversation.getConversationId());
        session.setAttribute("currentChatMessages", messages);
        session.setAttribute("currentConversationId", conversation.getConversationId());

        response.sendRedirect("sendMessage?conversationId=" + conversation.getConversationId() + "&recipient=" + recipientUsername);
    }
}