
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.CourseCategoryDAO;
import dal.CourseDAO;
import dal.MentorPostDAO;
import dal.UserDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Course;
import model.MentorPost;
import model.MentorPostComment;
import model.User;

/**
 *
 * @author Huy Võ
 */
public class ManageCourse extends HttpServlet {

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
            out.println("<title>Servlet manageCourse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet manageCourse at " + request.getContextPath() + "</h1>");
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
        CourseDAO daoC = new CourseDAO();
        UserDAO daoU = new UserDAO();
        String courseIdString = request.getParameter("courseId");
        int courseId = Integer.parseInt(courseIdString);
        String mentorName = request.getParameter("mentorName");
        List<Course> courses = daoC.getAll();
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseId() == courseId) {
                course = c;
            }
        }
        int member = daoC.getTotalParticipants(course.getCourseId(), 1, mentorName);
        int rmember = daoC.getTotalParticipants(course.getCourseId(), 0, mentorName);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        MentorPostDAO mentorPostDAO = new MentorPostDAO();
        List<MentorPost> posts = mentorPostDAO.getAllPost(course.getCourseId(), mentorName);
        Map<Integer, List<MentorPostComment>> postComments = new HashMap<>();
        for (MentorPost post : posts) {
            List<MentorPostComment> comments = mentorPostDAO.getAllCommentsByPostId(post.getPostId());
            postComments.put(post.getPostId(), comments);
        }
        List<String> menteeUsername = daoC.getMenteeByCourse(courseId, 1, mentorName);
        List<String> requestUsername = daoC.getMenteeByCourse(courseId, 0, mentorName);
        List<User> listMentee = mentorPostDAO.getMenteeList(courseId, 1, mentorName);
        List<User> listRequest = mentorPostDAO.getMenteeList(courseId, 0, mentorName);
        List<User> listUser = mentorPostDAO.getUserList(courseId, 1, mentorName);
        Map<String, User> userMap = new HashMap<>();
        for (User u : listUser) {
            userMap.put(u.getUsername(), u);
        }
        session.setAttribute("userMap", userMap);
        session.setAttribute("member", member);
        session.setAttribute("rmember", rmember);
        session.setAttribute("course", course);
        session.setAttribute("posts", posts);
        session.setAttribute("listMentee", listMentee);
        session.setAttribute("listRequest", listRequest);
        session.setAttribute("listUser", listUser);
        session.setAttribute("postComments", postComments);
        request.getRequestDispatcher("manageCourse.jsp").forward(request, response);
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
        processRequest(request, response);
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
