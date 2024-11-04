/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CourseDAO;
import dal.ParticipateDAO;
import dal.RequestDAO;
import dal.StatusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Course;
import model.Participate;
import model.Request;
import model.Status;

/**
 *
 * @author Admin
 */
public class EditRequestForMentor extends HttpServlet {

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
            out.println("<title>Servlet EditRequestForMentor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditRequestForMentor at " + request.getContextPath() + "</h1>");
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
        String username = request.getParameter("username");
        String courseId_str = request.getParameter("courseId");
        RequestDAO daoR = new RequestDAO();
        CourseDAO daoC = new CourseDAO();
        StatusDAO daoS = new StatusDAO();
        try {
            int courseId = Integer.parseInt(courseId_str);
            List<Course> c = daoC.getAllCoursesByUsernameOfMentor(username);
            List<Request> requests = daoR.getAllRequestByUsername(username);
            List<Course> additionalCourses = new ArrayList<>();
            for (Request r : requests) {
                boolean found = false;
                for (Course c1 : c) {
                    if (r.getCourseId() == c1.getCourseId()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Course course = daoC.getCourseByCourseId(r.getCourseId());
                    additionalCourses.add(course);
                }
            }
            c.addAll(additionalCourses);
            Course thisCourse = daoC.getCourseByCourseId(courseId);
            Request req = daoR.getRequestByUsername(username, courseId);
            List<Course> courses = daoC.getAll();
            List<Status> status = daoS.getAll();
            List<Course> otherCourse = daoC.getOtherCourses(c);
            otherCourse.add(thisCourse);
            Map<Integer, Integer> indexMap = IntStream.range(0, courses.size())
                    .boxed()
                    .collect(Collectors.toMap(i -> courses.get(i).getCourseId(), i -> i));

            otherCourse.sort(Comparator.comparingInt(course -> indexMap.getOrDefault(course.getCourseId(), Integer.MAX_VALUE)));
            session.setAttribute("courses", courses);
            session.setAttribute("status", status);
            session.setAttribute("req", req);
            session.setAttribute("otherCourse", otherCourse);
            response.sendRedirect("viewRequestForMentor.jsp");
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
        String username = request.getParameter("username");
        String oldCourseId_str = request.getParameter("oldCourseId");
        String newCourseId_str = request.getParameter("newCourseId");
        String requestReason = request.getParameter("requestReason");
        RequestDAO daoR = new RequestDAO();
        ParticipateDAO daoP = new ParticipateDAO();

        try {
            int oldCourseId = Integer.parseInt(oldCourseId_str);
            int newCourseId = Integer.parseInt(newCourseId_str);
            Request req1 = daoR.getRequestByUsername(username, oldCourseId);
            daoP.addParticipate(new Participate(newCourseId, req1.getUsername(), 2, req1.getRequestStatus(), req1.getUsername()));
            daoR.updateRequest(oldCourseId, newCourseId, username, requestReason);
            daoP.deleteParticipate(oldCourseId, username);
            Request req2 = daoR.getRequestByUsername(username, newCourseId);
            session.setAttribute("message", "*Update Successfully!");
            session.setAttribute("req", req2);
            response.sendRedirect("viewRequestForMentor.jsp");
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
