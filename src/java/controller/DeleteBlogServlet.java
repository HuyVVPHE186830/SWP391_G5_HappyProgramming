package controller;

import dal.BlogDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import model.Blog;
import model.User;

public class DeleteBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("id"));
        BlogDAO blogDAO = new BlogDAO();

        // Check if blog exists and if user is the author
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        Blog blog = blogDAO.getBlogById(blogId);
        
        if (blog != null && blog.getCreatedBy().equals(username)) {
            blogDAO.deleteBlog(blogId);  // Call DAO method to delete the blog
        }

        response.sendRedirect("viewblogs");  // Redirect to blog list after deletion
    }
}
