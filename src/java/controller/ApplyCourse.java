/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CourseDAO;
import dal.ParticipateDAO;
import dal.RequestDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Course;
import model.Participate;
import model.Request;
import model.User;

/**
 *
 * @author Admin
 */
public class ApplyCourse extends HttpServlet {

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
            out.println("<title>Servlet applyCourse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet applyCourse at " + request.getContextPath() + "</h1>");
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
        UserDAO daoU = new UserDAO();
        CourseDAO daoC = new CourseDAO();
        RequestDAO daoR = new RequestDAO();
        String userId_str = request.getParameter("userId");
        try {
            int userId = Integer.parseInt(userId_str);
            User user = daoU.getUserById(userId);
            List<Course> courses = daoC.getAllCoursesByUsernameOfMentor(user.getUsername());
            List<Request> requests = daoR.getAllRequestByUsername(user.getUsername());
            List<Course> additionalCourses = new ArrayList<>();
            for (Request r : requests) {
                boolean found = false;
                for (Course c : courses) {
                    if (r.getCourseId() == c.getCourseId()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Course course = daoC.getCourseByCourseId(r.getCourseId());
                    additionalCourses.add(course);
                }
            }
            courses.addAll(additionalCourses);
            List<Course> otherCourses = daoC.getOtherCourses(courses);
            session.setAttribute("otherCourse", otherCourses);
            response.sendRedirect("applyCourse.jsp");
        } catch (Exception e) {
            e.printStackTrace();
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
        String username = request.getParameter("username");
        String courseId_str = request.getParameter("courseId");
        String requestReason = request.getParameter("requestReason");
        ParticipateDAO daoP = new ParticipateDAO();
        UserDAO daoU = new UserDAO();
        RequestDAO daoR = new RequestDAO();
        CourseDAO daoC = new CourseDAO();
        Date date = new Date();
        try {
            User u = daoU.getUserByUsernameM(username);
            int courseId = Integer.parseInt(courseId_str);
            Participate p = daoP.getParticipateByUsernameAndCourseId(courseId, username);
            daoP.addParticipate(new Participate(courseId, username, 2, 0, username));
            daoR.addRequest(new Request(courseId, username, date, 0, requestReason, username));
            List<Course> courses = daoC.getAllCoursesByUsernameOfMentor(username);
            List<Request> requests = daoR.getAllRequestByUsername(username);
            List<Course> additionalCourses = new ArrayList<>();
            for (Request r : requests) {
                boolean found = false;
                for (Course c : courses) {
                    if (r.getCourseId() == c.getCourseId()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Course course = daoC.getCourseByCourseId(r.getCourseId());
                    additionalCourses.add(course);
                }
            }
            courses.addAll(additionalCourses);
            List<Course> otherCourses = daoC.getOtherCourses(courses);
            session.setAttribute("otherCourse", otherCourses);
            session.setAttribute("message", "Your request has been submitted successfully! Please allow some time for it to be reviewed and approved.");
            response.sendRedirect("applyCourse?userId=" + u.getId());
        } catch (Exception e) {
            e.printStackTrace();
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
