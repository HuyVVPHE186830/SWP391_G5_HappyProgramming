package admin;

import dal.StatisticsDAO;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class StatisticsController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StatisticsDAO dao = new StatisticsDAO();

        // Get filter parameters
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String user = request.getParameter("user");
        String createdDate = request.getParameter("createdDate");
        String username = request.getParameter("username");
        Integer status = request.getParameter("status") != null && !request.getParameter("status").isEmpty()
                ? Integer.parseInt(request.getParameter("status"))
                : null;

        // Fetch statistics based on filters
        Map<String, Integer> userRolesStats = dao.getUserRolesStats();
        Map<String, Integer> courseStats = dao.getCoursesStats();
        Map<String, Integer> userBlogStats = dao.getUserBlogStats(startDate, endDate, user);  // Pass filters here
        Map<String, Integer> messageStats = dao.getMessagesStats();
        Map<String, Integer> requestStats = dao.getRequestStats(createdDate, username, status); // Fetch request stats

        // Fetch all users for the dropdown
        List<String> allUsers = dao.getAllUsernames();

        // Set attributes for JSP
        request.setAttribute("userRolesStats", userRolesStats);
        request.setAttribute("courseStats", courseStats);
        request.setAttribute("userBlogStats", userBlogStats);
        request.setAttribute("messageStats", messageStats);
        request.setAttribute("requestStats", requestStats); // Set request stats
        request.setAttribute("allUsers", allUsers);

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
