
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
import model.User;
import util.Email;

/**
 *
 * @author mONESIUU
 */
public class VerifyServlet extends HttpServlet {

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

        request.getRequestDispatcher("verify.jsp").forward(request, response);
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
            HttpSession session = request.getSession();
            String passWord = request.getParameter("passWord");
            String newPass = request.getParameter("newPass");
            String confirmPass = request.getParameter("confirmPass");

            UserDAO dao = new UserDAO();
            List<User> users = dao.getAll();
            boolean userFound = false;
            User foundUser = null;
            for (User u : users) {
                if (passWord.equals(u.getPassword())) {
                    userFound = true; 
                    foundUser = u;
                    break; 
                }
            }
            if (!userFound) {
                request.setAttribute("error", "Password is invalid.");
                request.getRequestDispatcher("verify.jsp").forward(request, response);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                request.setAttribute("error", "Passwords do not match.");
                request.getRequestDispatcher("verify.jsp").forward(request, response);
                return;
            }

            boolean passwordChanged = dao.resetPassWord(foundUser.getPassword(), newPass);
            if (passwordChanged) {
                request.setAttribute("success", "Password has been changed successfully.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to change password. Please try again.");
                request.getRequestDispatcher("verify.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
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
