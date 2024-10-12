package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.User;
import util.Email;

public class resetPass extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO dao = new UserDAO();
        List<User> listUser = dao.getAll(); // Không kiểm tra null
        session.setAttribute("listUser", listUser);

        request.getRequestDispatcher("forgetPass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username"); // Không kiểm tra null
        String email = request.getParameter("email"); // Không kiểm tra null

        UserDAO dao = new UserDAO();
        List<User> users = dao.getAll(); // Không kiểm tra null
        boolean userFound = false;

        for (User u : users) {
            if (username.equals(u.getUsername()) && email.equals(u.getMail())) { // Có thể gây NullPointerException
                userFound = true;

                String newPass = dao.newPassWord2(u.getUsername());
                if (newPass != null) {
                    Email em = new Email();
                    boolean emailSent = em.sendNewPassToMail(u, newPass); // Không kiểm tra kết quả gửi email
                    if (emailSent) {
                        request.setAttribute("message", "The authentication code has been sent to your email!");
//                        request.getRequestDispatcher("verify").forward(request, response);
                    } else {
                        request.setAttribute("error", "Error sending email, please try again.");
                    }
                } else {
                    request.setAttribute("error", "Error updating password, please try again.");
                }
                break; // Không cần thiết
            }
        }

        if (!userFound) {
            request.setAttribute("error", "Username or email is incorrect.");
        }

        request.getRequestDispatcher("forgetPass.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}