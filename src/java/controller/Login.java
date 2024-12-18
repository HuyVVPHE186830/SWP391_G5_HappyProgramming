/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ConversationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.User;
import dal.UserDAO;
import model.Conversation;
import model.GoogleAccount;

/**
 * 4
 *
 * @author Admin
 */
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        String error = request.getParameter("error");
        if (error != null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code);
        GoogleAccount acc = gg.getUserInfo(accessToken);

        UserDAO dao = new UserDAO();
        User user = dao.getUserByMail(acc.getEmail());

        if (user != null) {
            session.setAttribute("user", user);
            response.sendRedirect("home");
        } else {
            session.setAttribute("email", acc.getEmail());
            session.setAttribute("firstName", acc.getGiven_name());
            session.setAttribute("lastName", acc.getFamily_name());
            response.sendRedirect("googlelogin.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDAO dao = new UserDAO();
        List<User> users = dao.getAll();
        boolean found = false;
        boolean active = true;

        for (User u : users) {
            if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                if (!u.isActiveStatus()) {
                    active = false;
                    break;
                }
                found = true;
                session.setAttribute("user", u);

                // Lấy cuộc hội thoại mới nhất cho người dùng
                ConversationDAO conversationDAO = new ConversationDAO();
                Conversation latestConversation = conversationDAO.getLatestConversationWithMessageForUser(u.getUsername());

                // Cập nhật lastConversationId trong session
                if (latestConversation != null) {
                    int lastConversationId = latestConversation.getConversationId();
                    session.setAttribute("lastConversationId", lastConversationId);
                } else {
                    // Không có cuộc hội thoại nào
                    session.setAttribute("lastConversationId", null);
                }

                // Chuyển hướng đến trang home
                response.sendRedirect("home");
                return; // Ngừng thực thi sau khi chuyển hướng
            }
        }

        if (!active) {
            session.setAttribute("error", "Your Account Have Been Deactivate");
            response.sendRedirect("login.jsp");
            return;
        }

        if (!found) {
            session.setAttribute("error", "Check Your Username Or Password");
            response.sendRedirect("login.jsp");
            return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
