                    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.CourseDAO;
import dal.ParticipateDAO;
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
import model.Category;
import model.Course;
import model.Participate;
import model.User;
import util.PageControl;

/**
 *
 * @author Admin
 */
public class home extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet home</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet home at " + request.getContextPath() + "</h1>");
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
        ParticipateDAO daoP = new ParticipateDAO();
        CategoryDAO daoCt = new CategoryDAO();
        List<User> mentor = daoU.getAllUserByRoleId(2);
        List<Course> course = daoC.getEachCategoryLessThan2Courses();
        List<Category> category = daoCt.getAll();
        List<User> mentee = daoU.getAllUserByRoleId(3);
        List<Course> sortedCourse = daoC.getAllCourseOrderByDESCMenteeNum();
        List<Participate> participate = daoP.getAll();
        int countCourse = daoC.countCourse();
        int countMentor = daoU.countUser(2);
        int countMentee = daoU.countUser(3);
        session.setAttribute("countCourse", countCourse);
        session.setAttribute("countMentor", countMentor);
        session.setAttribute("countMentee", countMentee);
        session.setAttribute("category", category);
        session.setAttribute("mentor", mentor);
        session.setAttribute("course", course);
        session.setAttribute("mentee", mentee);
        session.setAttribute("participate", participate);
        session.setAttribute("sortedCourses", sortedCourse);
        PageControl pageControl = new PageControl();
        List<Course> listCourse = findCourseDoGet(request, pageControl);
        session.setAttribute("listCourse", listCourse);
        session.setAttribute("pageControl", pageControl);
        if (session.getAttribute("user") != null) {
            User u = (User) session.getAttribute("user");
            if (u.getRoleId() == 1) {
                session.setAttribute("user", u);
                response.sendRedirect("ManagerAccount");
            } else if (u.getRoleId() == 2) {
                session.setAttribute("user", u);
                response.sendRedirect("homementor.jsp");
            } else if (u.getRoleId() == 3) {
                session.setAttribute("user", u);
                response.sendRedirect("homementee.jsp");
            }
        } else {
            response.sendRedirect("homeguest.jsp");
        }
    }

    private List<Course> findCourseDoGet(HttpServletRequest request, PageControl pagecontrol) {
        CourseDAO dao = new CourseDAO();
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
        List<Course> listCourse;
        String requestURL = request.getRequestURL().toString();
        int totalRecord = 0;
        totalRecord = dao.findTotalRecordAllCourses();
        listCourse = dao.getAllCourse2(page);
        pagecontrol.setUrlPattern(requestURL + "?");
        int totalPage = (totalRecord % 6) == 0
                ? (totalRecord / 6)
                : (totalRecord / 6) + 1;

        pagecontrol.setPage(page);
        pagecontrol.setTotalPage(totalPage);
        pagecontrol.setTotalRecord(totalRecord);

        return listCourse;
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
        response.sendRedirect("home");
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
