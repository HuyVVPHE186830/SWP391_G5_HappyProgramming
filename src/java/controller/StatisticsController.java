package controller;

import dal.StatisticsDAO;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StatisticsController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StatisticsDAO dao = new StatisticsDAO();

        // Fetch statistics
        Map<String, Integer> userRolesStats = dao.getUserRolesStats();
        Map<String, Integer> courseStats = dao.getCoursesStats();
        Map<String, Integer> userBlogStats = dao.getUserBlogStats();
        Map<String, Integer> messageStats = dao.getMessagesStats();

        // Set the attributes for JSP to access
        request.setAttribute("userRolesStats", userRolesStats);
        request.setAttribute("courseStats", courseStats);
        request.setAttribute("userBlogStats", userBlogStats);
        request.setAttribute("messageStats", messageStats);

        // Forward to JSP
        request.getRequestDispatcher("dashboard/statistics.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
