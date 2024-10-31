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
public class RatingController extends HttpServlet {

    RatingDAO rateDAO = new RatingDAO();
    UserDAO userDAO = new UserDAO();
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<User> userList = userDAO.getAll();
        int userID = Integer.parseInt(request.getParameter("ratedId"));

        User userById = userDAO.getUserById(userID);
        float userRatedStar  = rateDAO.getAverageStar(userID);
        int rankStar = rateDAO.getRankMentor(userID);
        List<User> listRatedFrom = userDAO.getAll();
        List<Integer> listDis = rateDAO.getDistinctNoStars();
        List<Course> listCourseOfRated = rateDAO.getCourseOfRated(userID);
        List<Rating> listFeedBack = findRatingDoGet(request);
        int turnStar = rateDAO.getTurnStarOverallByUserId(userID);
        int turnStar1 = rateDAO.getTurnStar(1,userID);
        int turnStar2 = rateDAO.getTurnStar(2,userID);
        int turnStar3 = rateDAO.getTurnStar(3,userID);
        int turnStar4 = rateDAO.getTurnStar(4,userID);
        int turnStar5 = rateDAO.getTurnStar(5,userID);
        session.setAttribute("turnStar", turnStar);
        session.setAttribute("turnStar1", turnStar1);
        session.setAttribute("turnStar2", turnStar2);
        session.setAttribute("turnStar3", turnStar3);
        session.setAttribute("turnStar4", turnStar4);
        session.setAttribute("turnStar5", turnStar5);
   
        session.setAttribute("listRatedFrom", listRatedFrom);
        session.setAttribute("listCourseOfRated", listCourseOfRated);
        session.setAttribute("rankStar", rankStar);
        session.setAttribute("userRatedStar", userRatedStar);
        session.setAttribute("listFeedBack", listFeedBack);
        session.setAttribute("listDis", listDis);
        session.setAttribute("userList", userList);
        session.setAttribute("userById", userById);
        request.getRequestDispatcher("viewMentorFeedBack.jsp").forward(request, response);

    }

    private List<Rating> findRatingDoGet(HttpServletRequest request) {
        String actionSearch = request.getParameter("search") == null
                ? "default"
                : request.getParameter("search");
        List<Rating> listRate;
        String requestURL = request.getRequestURL().toString();
        switch (actionSearch) {
            case "search-by-noStar":
                int ratedID3 = Integer.parseInt(request.getParameter("ratedId"));

                int numS = Integer.parseInt(request.getParameter("noStar"));
                String us = request.getParameter("userN");
                listRate = rateDAO.getRateByNoStar2(numS, us);
                request.setAttribute("urlPattern", requestURL + "?search=search-by-noStar&noStar=" + numS+"&ratedId=${userById.id}&&userN"+us);
                break;
            case "feedback":
                int ratedID = Integer.parseInt(request.getParameter("ratedId"));
                listRate = rateDAO.getRateByUserId(ratedID);
                request.setAttribute("urlPattern", requestURL + "?search=feedback&ratedId=" + ratedID);
                break;
            case "rate-by-course":
                int courseID = Integer.parseInt(request.getParameter("courseid"));
                int ratedID4 = Integer.parseInt(request.getParameter("ratedId"));
                listRate = rateDAO.getRateByCourse(courseID,ratedID4);
                request.setAttribute("urlPattern", requestURL + "?search=rate-by-course&ratedId="+ratedID4+"&courseid="+courseID);
                break;

            default:
                int ratedID2 = Integer.parseInt(request.getParameter("ratedId"));
                listRate = rateDAO.getRateByUserId(ratedID2);
                request.setAttribute("urlPattern", requestURL + "?ratedId=" + ratedID2);
        }
        return listRate;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Rating");

    }

    @Override

    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
