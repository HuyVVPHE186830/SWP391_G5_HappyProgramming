
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.CourseDAO;
import dal.ParticipateDAO;
import dal.RatingDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Category;
import model.Course;
import model.Participate;
import model.Rating;
import model.User;

/**
 *
 * @author Admin
 */
public class ViewMentor extends HttpServlet {

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
            out.println("<title>Servlet viewMentor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet viewMentor at " + request.getContextPath() + "</h1>");
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
        RatingDAO rateDAO = new RatingDAO();
        CourseDAO daoC = new CourseDAO();
        CategoryDAO daoCt = new CategoryDAO();
        ParticipateDAO daoP = new ParticipateDAO();

        String userId_str = request.getParameter("userId");
        if (request.getParameter("courseId") == null) {

        }
        String courseId_str = request.getParameter("courseId");
        int courseID = Integer.parseInt(request.getParameter("courseId"));
        int ratedToUser = Integer.parseInt(request.getParameter("userId"));
        try {
            int courseId = Integer.parseInt(courseId_str);
            int userId = Integer.parseInt(userId_str);
            Course course = daoC.getCourseByCourseId(courseId);
            float avg = rateDAO.getAverageStar(userId);
            User mentor = daoU.getUserById(userId);
            List<Category> cate = daoCt.getAllCategoryByCourseId(courseId);
            List<Participate> participate = daoP.getAll();
            List<User> othermentor = daoU.getAllMentorByCourseIdExceptOne(courseId, mentor.getUsername());
            List<Course> othercourse = daoC.getAllCoursesOfMentorExceptOne(courseId, mentor.getUsername());
            List<Rating> rateList = rateDAO.getAll();
            int rateListByUsernameCID = rateDAO.getByUsnIdAndCId(ratedToUser, courseID);
            request.setAttribute("avg", avg);
            request.setAttribute("participate", participate);
            request.setAttribute("rateListByUsernameCID", rateListByUsernameCID);
            request.setAttribute("rateList", rateList);
            request.setAttribute("otherCourseMentor", othercourse);
            request.setAttribute("mentorDetail", mentor);
            request.setAttribute("categories", cate);
            request.setAttribute("otherMentor", othermentor);
            request.setAttribute("courseOfMentor", course);

            request.getRequestDispatcher("viewmentor.jsp").forward(request, response);
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
