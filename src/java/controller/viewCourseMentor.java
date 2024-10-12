/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CourseDAO;
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
import model.User;

/**
 *
 * @author Admin
 */
public class viewCourseMentor extends HttpServlet {

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
            out.println("<title>Servlet viewCourseMentor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet viewCourseMentor at " + request.getContextPath() + "</h1>");
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
        String orderby = request.getParameter("orderby");
        HttpSession session = request.getSession();
        UserDAO daoU = new UserDAO();
        CourseDAO daoC = new CourseDAO();
        String courseId_str = request.getParameter("courseId");

        try {

            int courseId = Integer.parseInt(courseId_str);
            List<User> mentor = new ArrayList<>();
            if (orderby.equals("default")) {
                mentor = daoU.getAllMentorByCourseId(courseId);
            }
            if (orderby.equals("name")) {
                mentor = daoU.getAllMentorByCourseIdOrderByName(courseId);
            }
            List<Course> otherCourse = daoC.getAllCoursesExceptOne(courseId);
            Course course = daoC.getCourseByCourseId(courseId);
            session.setAttribute("mentorThisCourse", mentor);
            session.setAttribute("courseOfMentor", course);
            session.setAttribute("order", orderby);
            session.setAttribute("otherCourseExO", otherCourse);
            response.sendRedirect("viewCourseMentor.jsp");
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
        UserDAO daoU = new UserDAO();
        CourseDAO daoC = new CourseDAO();
        String courseId_str = request.getParameter("courseId");
        String keyword = request.getParameter("keyword");
        try {

            int courseId = Integer.parseInt(courseId_str);
            List<User> mentor = daoU.getAllMentorBySearchKey(courseId, keyword);
            Course course = daoC.getCourseByCourseId(courseId);
            List<Course> otherCourse = daoC.getAllCoursesExceptOne(courseId);

            session.setAttribute("mentorThisCourse", mentor);
            session.setAttribute("courseOfMentor", course);
            session.setAttribute("otherCourseExO", otherCourse);
            response.sendRedirect("viewCourseMentor.jsp");
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
