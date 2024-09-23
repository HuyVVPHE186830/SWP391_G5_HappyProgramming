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
import java.util.List;
import model.Category;
import model.Course;
import model.Course_Category;
import model.User;
import util.PageControl;

/**
 *
 * @author mONESIUU
 */
public class viewAllCourse extends HttpServlet {

    CourseDAO courseDAO = new CourseDAO();
    UserDAO userDAO = new UserDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        List<Category> listCategory = courseDAO.getAllCategories();
        List<Course_Category> listCourse_Category = courseDAO.getAllCategories_Course();
        List<User> listMentor = courseDAO.getAllMentor();
        List<User> listUser = userDAO.getAll();
        List<Course> listMostEnrollCourse = userDAO.getCourseByQuantityEnroll();
        PageControl pageControl = new PageControl();
        List<Course> listCourse = findCourseDoGet(request, pageControl);
        HttpSession session = request.getSession();

        session.setAttribute("listMostEnrollCourse", listMostEnrollCourse);
        session.setAttribute("listUser", listUser);
        session.setAttribute("listCategory", listCategory);
        session.setAttribute("listMentor", listMentor);
        session.setAttribute("listCourse_Category", listCourse_Category);
        session.setAttribute("listCourse", listCourse);

        request.getRequestDispatcher("listCourse.jsp").forward(request, response);
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
        response.sendRedirect("allCourse");

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

    private List<Course> findCourseDoGet(HttpServletRequest request, PageControl pagecontrol) {
        String pageRaw = request.getParameter("page");
        //valid
        int page;
        try {
            page = Integer.parseInt(pageRaw);
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        String actionSearch = request.getParameter("search") == null
                ? "default"
                : request.getParameter("search");
        List<Course> listCourse;
        String requestURL = request.getRequestURL().toString();
        int totalRecord = 0;
        switch (actionSearch) {
            case "searchByName":
                String keyword = request.getParameter("keyword");
//                totalRecord = productDAO.findTotalRecordByName(keyword);
                totalRecord = courseDAO.findTotalRecordByName(keyword);
                listCourse = courseDAO.findByName(keyword, page);
//                pagecontrol.setUrlPattern(requestURL + "?search=searchByName&keyword=" + keyword + "&");
                pagecontrol.setUrlPattern(requestURL + "?search=searchByName&keyword=" + keyword + "&");
                break;
            case "category":
                String categoryId = request.getParameter("categoryId");
//                totalRecord = courseDAO.findTotalRecordByCategory(categoryId);
                listCourse = courseDAO.findByCategory(categoryId);
                pagecontrol.setUrlPattern(requestURL + "?search=category&categoryId=" + categoryId + "&");
                break;
            case "username":
                String username = request.getParameter("username");
//                totalRecord = courseDAO.findTotalRecordByCategory(categoryId);
                listCourse = courseDAO.findByUsername2(username);
                pagecontrol.setUrlPattern(requestURL + "?search=username&username=" + username + "&");
                break;

            default:
                listCourse = courseDAO.getAllCourse();
                pagecontrol.setUrlPattern(requestURL + "?");
        }
        int totalPage = (totalRecord % 12) == 0
                ? (totalRecord / 12)
                : (totalRecord / 12) + 1;

        pagecontrol.setPage(page);
        pagecontrol.setTotalPage(totalPage);
        pagecontrol.setTotalRecord(totalRecord);

        return listCourse;
    }
}