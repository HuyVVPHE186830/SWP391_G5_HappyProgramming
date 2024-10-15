package controller;

import dal.BlogDAO;
import model.Blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/searchBlogs")
public class SearchBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs = blogDAO.searchBlogs(query); // Perform search based on the query
        
        request.setAttribute("blogs", blogs); // Set the search results as a request attribute

        request.getRequestDispatcher("listpost.jsp").forward(request, response); // Forward to the forum.jsp to display results
    }
}
