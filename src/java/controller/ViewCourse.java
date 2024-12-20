
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.CourseCategoryDAO;
import dal.CourseDAO;
import dal.RatingDAO;
import dal.UserDAO;
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
import java.util.stream.Collectors;
import model.Category;
import model.Course;
import model.CourseCategory;
import model.User;

/**
 *
 * @author Admin
 */
public class ViewCourse extends HttpServlet {

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
            out.println("<title>Servlet viewcourse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet viewcourse at " + request.getContextPath() + "</h1>");
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
        RatingDAO daoR = new RatingDAO();
        String courseId_str = request.getParameter("courseId");
        CategoryDAO daoCt = new CategoryDAO();
        CourseCategoryDAO daoCC = new CourseCategoryDAO();
        UserDAO daoU = new UserDAO();
        try {
            int courseId = Integer.parseInt(courseId_str);
            List<User> mentorRated = daoU.getAllMentorByCourseId(courseId)
                    .stream()
                    .collect(Collectors.toMap(
                            User::getUsername,
                            mentor -> mentor,
                            (existing, replacement) -> existing
                    ))
                    .values()
                    .stream()
                    .collect(Collectors.toList());
            Map<String, Float> mentorAvgRatingMap = new HashMap<>();
            for (User mentor : mentorRated) {
                float averageStar = daoR.getAverageStar(mentor.getId());
                mentorAvgRatingMap.put(mentor.getUsername(), averageStar);
            }
            List<User> topMentors = mentorRated.stream()
                .sorted((m1, m2) -> Float.compare(mentorAvgRatingMap.get(m2.getUsername()), mentorAvgRatingMap.get(m1.getUsername())))
                .limit(4)
                .collect(Collectors.toList());
            Course c = daoC.getCourseByCourseId(courseId);
            List<Integer> sameCategoryId = daoCC.getCategoryIdByCourseId(courseId);
            List<Course> sameCourse = daoC.getSameCourse(courseId);
            Category cate = daoCt.getCategoryByCourseId(courseId);
            List<Course> otherCourse = daoC.getOtherCourseHasOtherCategory(sameCategoryId);
            List<Category> allCategory = daoCt.getAll();
            List<Category> category = daoCt.getAllExceptOne(sameCategoryId);

            request.setAttribute("category", allCategory);
            request.setAttribute("sameCateId", sameCategoryId);
            request.setAttribute("othercategory", category);
            request.setAttribute("otherCourse", otherCourse);
            request.setAttribute("sameCourse", sameCourse);
            request.setAttribute("categoryCourse", cate);
            request.setAttribute("courseDetail", c);
            request.setAttribute("mentorThisCourse", topMentors);
            request.getRequestDispatcher("viewcourse.jsp").forward(request, response);
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
