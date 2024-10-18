package controller;

import dal.BlogCommentDAO;
import dal.BlogDAO;
import model.Blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.BlogComment;

public class ViewBlogDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the blog ID from the request
        String blogIdParam = request.getParameter("id");
        int blogId = Integer.parseInt(blogIdParam);

        // Retrieve the blog details from the DAO
        BlogDAO blogDAO = new BlogDAO();
        Blog blog = blogDAO.getBlogById(blogId);

        BlogCommentDAO blogCommentDAO = new BlogCommentDAO();
        List<BlogComment> comments = blogCommentDAO.getCommentsForBlog(blogId);
        
        // Set the blog as an attribute in the request
        request.setAttribute("blog", blog);
        request.setAttribute("comments", comments);

        // Forward to the JSP page
        request.getRequestDispatcher("viewBlogDetails.jsp").forward(request, response);
    }
}