/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import java.util.List;
import model.Course;
import model.Rating;
import model.User;

/**
 *
 * @author mONESIUU
 */
public class MentorFeedBack extends HttpServlet {

    RatingDAO rateDAO = new RatingDAO();
    UserDAO userDAO = new UserDAO();
    CourseDAO courseDAO = new CourseDAO();

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
            out.println("<title>Servlet MentorFeedBack</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MentorFeedBack at " + request.getContextPath() + "</h1>");
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
    protected List<Rating> findRatingGet(HttpServletRequest request) {
        int ratedID = Integer.parseInt(request.getParameter("ratedId"));
        List<Rating> ratingList;
        String actionSearch = request.getParameter("search") == null
                ? "default"
                : request.getParameter("search");
        switch (actionSearch) {
            case "search-by-noStar":
                int numS = Integer.parseInt(request.getParameter("noStar"));
                ratingList = rateDAO.getRateByNoStar3(numS, ratedID);
                break;
            default:
                ratingList = rateDAO.getRateByUserId(ratedID);
        }
        return ratingList;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int ratedID = Integer.parseInt(request.getParameter("ratedId"));
        float userRatedStar = rateDAO.getAverageStar(ratedID);
        List<Course> listCourseOfRated = rateDAO.getCourseByRated(ratedID);
        int rankStar = rateDAO.getRankMentor(ratedID);
        List<Rating> ratingList = findRatingGet(request);
        List<User> listRatedFromToId = rateDAO.getListRatedFromToId(ratedID);
//                findRatingGet(request);
        int turnStar = rateDAO.getTurnStarOverallByUserId(ratedID);
        int turnStar1 = rateDAO.getTurnStar(1, ratedID);
        int turnStar2 = rateDAO.getTurnStar(2, ratedID);
        int turnStar3 = rateDAO.getTurnStar(3, ratedID);
        int turnStar4 = rateDAO.getTurnStar(4, ratedID);
        int turnStar5 = rateDAO.getTurnStar(5, ratedID);

        session.setAttribute("turnStar", turnStar);
        session.setAttribute("turnStar1", turnStar1);
        session.setAttribute("turnStar2", turnStar2);
        session.setAttribute("turnStar3", turnStar3);
        session.setAttribute("turnStar4", turnStar4);
        session.setAttribute("turnStar5", turnStar5);

        session.setAttribute("listCourseOfRated", listCourseOfRated);
        session.setAttribute("rankStar", rankStar);
        session.setAttribute("userRatedStar", userRatedStar);
        session.setAttribute("listRatedFromToId", listRatedFromToId);
        session.setAttribute("ratingList", ratingList);
        User ratedUser = userDAO.getUserById(ratedID);
        session.setAttribute("ratedUser", ratedUser);
        request.getRequestDispatcher("viewMentorFeedBack.jsp").forward(request, response);

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
        HttpSession session = request.getSession(false);
        int ratedID = Integer.parseInt(request.getParameter("ratedId"));
        String actioN = request.getParameter("action");

        switch (actioN) {
            case "rate-this-guy":
                int ratedFromID = Integer.parseInt(request.getParameter("userN"));
                int courssE = Integer.parseInt(request.getParameter("couRseId"));
                int noS = Integer.parseInt(request.getParameter("rating"));
                String com = request.getParameter("comment");
                List<Course> listCourseByBoth = courseDAO.getAll();
                session.setAttribute("listCourseByBoth", listCourseByBoth);
                session.setAttribute("ratedID", ratedID);
                rateDAO.addFeedbackById(ratedFromID, ratedID, noS, courssE, com);
                response.sendRedirect("rating?ratedId=" + ratedID);

                break;
            default:
                throw new AssertionError();
        }
    }
// @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String actioN = request.getParameter("action");
//
//        if ("rate-this-guy".equals(actioN)) {
//            // Lấy các tham số từ request
//            String ratedID = request.getParameter("ratedId");
//            String ratedFromId = request.getParameter("userN");
//            String courseId = request.getParameter("couRseId");
//            String rating = request.getParameter("rating");
//            String comment = request.getParameter("comment");
//
//            // Tạo chuỗi kết quả
//            String resultMessage = "Rated ID: " + ratedID + "<br>"
//                    + "Rated From: " + ratedFromId + "<br>"
//                    + "Course ID: " + courseId + "<br>"
//                    + "Rating: " + rating + "<br>"
//                    + "Comment: " + comment;
//
//            // Set chuỗi kết quả vào request
//            request.setAttribute("resultMessage", resultMessage);
//        } 
//
//        // Chuyển hướng đến result.jsp
//        request.getRequestDispatcher("result.jsp").forward(request, response);
//    }

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
