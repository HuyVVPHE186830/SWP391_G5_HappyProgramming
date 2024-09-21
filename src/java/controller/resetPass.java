/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Vector;
import model.User;
import util.Email;

/**
 *
 * @author mONESIUU
 */
public class resetPass extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO dao = new UserDAO();
        List<User> listUser = dao.getAll();
        session.setAttribute("listUser", listUser);

        request.getRequestDispatcher("forgetPass.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");

            UserDAO dao = new UserDAO();
            List<User> users = dao.getAll();
            boolean userFound = false;

            for (User u : users) {
                if (username.equals(u.getUsername()) && email.equals(u.getMail())) {
                    userFound = true; // Đánh dấu rằng người dùng đã tìm thấy
                    Email em = new Email();
                    boolean test = em.sendMail(u);
                    if (test) {
                        request.setAttribute("message", "The authentication code has been sent to your email!");
                    } else {
                        request.setAttribute("error", "Error, try again");
                    }
                    break; // Ngừng lặp sau khi tìm thấy người dùng
                }
            }

            // Nếu không tìm thấy người dùng tương ứng
            if (!userFound) {
                request.setAttribute("error", "Username or email is incorrect.");
            }

            // Chuyển tiếp đến trang JSP để hiển thị thông báo
            request.getRequestDispatcher("forgetPass.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("forgetPass.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
