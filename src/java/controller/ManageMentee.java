
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CourseDAO;
import dal.MentorPostDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.MentorPost;
import model.User;

/**
 *
 * @author Huy Võ
 */
public class ManageMentee extends HttpServlet {

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
            out.println("<title>Servlet manageMentee</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet manageMentee at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String action = request.getParameter("action");
        String mentorName = request.getParameter("mentorName");
        String fullname = request.getParameter("fullname");
        CourseDAO daoC = new CourseDAO();

        if ("accept".equals(action)) {
            daoC.setMenteeStatus(courseId, username, 1, mentorName);
            String success = "You accepted request of " + fullname;
            session.setAttribute("success", success);
        } else if ("decline".equals(action)) {
            String success = "You declined request of " + fullname;
            session.setAttribute("error", success);
            daoC.setMenteeStatus(courseId, username, -1, mentorName);
        }
        request.getRequestDispatcher("manageCourse?courseId=" + courseId + "&mentorName=" + mentorName).forward(request, response);
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
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String mentorName = request.getParameter("mentorName");
        String fullname = request.getParameter("fullname");
        CourseDAO daoC = new CourseDAO();
        daoC.setMenteeStatus(courseId, username, -1, mentorName);
        String success = "You banned " + fullname + " from this course";
        session.setAttribute("error", success);
        response.sendRedirect("manageCourse?courseId=" + courseId + "&mentorName=" + mentorName);
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
