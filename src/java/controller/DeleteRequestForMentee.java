package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.ParticipateDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author Admin
 */
public class DeleteRequestForMentee extends HttpServlet {

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
            out.println("<title>Servlet DeleteRequestForMentee</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteRequestForMentee at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ParticipateDAO daoP = new ParticipateDAO();
        UserDAO daoU = new UserDAO();
        String username = request.getParameter("username");
        String courseId_str = request.getParameter("courseId");
        try {
            User user = daoU.getUserByUsernameM(username);
            int courseId = Integer.parseInt(courseId_str);
            daoP.deleteParticipate(courseId, username);
            session.setAttribute("message", "Delete Successfully");
            response.sendRedirect("listRequestForMentee?userId=" + user.getId());
        } catch (Exception e) {
        }
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
        ParticipateDAO daoP = new ParticipateDAO();
        UserDAO daoU = new UserDAO();
        String username = request.getParameter("username");
        String mentorUsername = request.getParameter("mentorUsername");
        String courseId_str = request.getParameter("courseId");
        try {
            User user = daoU.getUserByUsernameM(mentorUsername);
            int courseId = Integer.parseInt(courseId_str);
            daoP.deleteParticipateForMentee(courseId, username, mentorUsername  );
            session.setAttribute("message", "Delete Successfully");
            response.sendRedirect("viewMentor?userId=" + user.getId() + "&courseId=" + courseId);
        } catch (Exception e) {
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
