
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
import model.User;
import util.Email;

/**
 *
 * @author Huy Võ
 */
public class VerifyEmail extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet verifyEmail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet verifyEmail at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String sendCode = request.getParameter("send");
        if ("true".equals(sendCode)) {
            Email emailService = new Email();
            boolean emailSent = emailService.sendVerifyEmail(user, request, response);
            
            if (emailSent) {
                session.setAttribute("message-verify", "Verification code has been sent to " + user.getMail());
            } else {
                session.setAttribute("error-message-verify", "Failed to send verification email. Please try again!");
            }
            request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
            return;
        }
        session.setAttribute("user", user);
        response.sendRedirect("verifyEmail.jsp");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String userEnteredCode = request.getParameter("verificationCode");
        String actualCode = (String) session.getAttribute("verificationCode");

        if (userEnteredCode.equals(actualCode)) {
            user.setIsVerified(true);
            session.setAttribute("verify", "Your account has been verified successfully!");
            UserDAO dao = new UserDAO();
            dao.verifyEmail(user.getUsername(), user);
            response.sendRedirect("userProfile.jsp");
        } else {
            session.setAttribute("error", "Invalid verification code.");
            response.sendRedirect("verifyEmail.jsp");
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
