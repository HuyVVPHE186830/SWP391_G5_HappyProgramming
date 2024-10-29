/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
        User userById = userDAO.getUserById(29);
        List<Integer> listDis = rateDAO.getDistinctNoStars();
        List<Rating> listFeedBack = findRatingDoGet(request);
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
                int numS = Integer.parseInt(request.getParameter("noStar"));
                listRate = rateDAO.getRateByNoStar(numS);
                request.setAttribute("urlPattern", requestURL + "?search=search-by-noStar&noStar=" + numS);
                break;
            default:
                listRate = rateDAO.getAll();
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
