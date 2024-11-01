package admin;

import dal.BlogDAO;
import model.Blog;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ManagerBlog extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogList = blogDAO.getAllBlogs();
        
        HttpSession session = request.getSession();
        session.setAttribute("blogList", blogList);

        request.getRequestDispatcher("dashboard/mngblog.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the search query from the request
        String searchQuery = request.getParameter("valueSearch");
        
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogList;
        
        // Check if searchQuery is not null or empty
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Search blogs based on the query
            blogList = blogDAO.searchBlogs(searchQuery);
        } else {
            // If no search query, retrieve all blogs
            blogList = blogDAO.getAllBlogs();
        }
        
        // Set the blogList attribute in the session
        HttpSession session = request.getSession();
        session.setAttribute("blogList", blogList);
        
        // Forward to the JSP page for displaying the blogs
        request.getRequestDispatcher("dashboard/mngblog.jsp").forward(request, response);
    }
}
