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
        String username = request.getParameter("username");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String action = request.getParameter("action"); // Lấy tham số action
        CourseDAO daoC = new CourseDAO();

        if ("accept".equals(action)) {
            daoC.banMentee(courseId, username, 1);
        } else if ("decline".equals(action)) {
            daoC.banMentee(courseId, username, -1);
        }

        response.sendRedirect("manageCourse?courseId=" + courseId);
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
        String username = request.getParameter("username");
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        CourseDAO daoC = new CourseDAO();

        daoC.banMentee(courseId, username, -1);

        response.sendRedirect("manageCourse?courseId=" + courseId);
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

    public static void main(String[] args) {
        CourseDAO daoC = new CourseDAO();
        UserDAO daoU = new UserDAO();
        List<String> menteeUsername = daoC.getMenteeByCourse(1, 1);
        List<User> listU = new ArrayList<User>();
        for (String string : menteeUsername) {
            User user = daoU.getUserByUsernameM(string);
            if (user != null) {
                listU.add(user);
            } else {
                System.out.println("User not found for username: " + string);
            }
        }
        for (User user : listU) {
            System.out.println(user.toString());
        }
    }
}
