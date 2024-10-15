package controller;

import dal.BlogDAO;
import model.Blog;
import model.Tag;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SubmitBlogServlet", urlPatterns = {"/submitBlog"})
@MultipartConfig
public class SubmitBlogServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve blog title, content, and tags
            String title = request.getParameter("blogTitle");
            String content = request.getParameter("blogContent");
            String tagsParam = request.getParameter("blogTags");
            
            // Split tags by comma and create a list
            List<Tag> tags = new ArrayList<>();
            if (tagsParam != null && !tagsParam.trim().isEmpty()) {
                String[] tagNames = tagsParam.split(",");
                for (String tagName : tagNames) {
                    tags.add(new Tag(0, tagName.trim())); // Tag ID will be assigned by the database
                }
            }
            
            // Handle image uploads
            List<String> imageUrls = new ArrayList<>();
            for (Part part : request.getParts()) {
                if (part.getName().equals("blogImages") && part.getSize() > 0) {
                    // Save the image and get its URL (you will need to implement saveImage() method)
                    String imageUrl = saveImage(part);
                    imageUrls.add(imageUrl);
                }
            }
            
            // Create a new Blog object and save it using BlogDAO
            Blog newBlog = new Blog(0, title, content, "username", imageUrls, tags); // Replace "username" with actual username
            BlogDAO blogDAO = new BlogDAO();
            blogDAO.addBlog(newBlog); // Method to insert the new blog into the database
            
            // Redirect to the blog list after submission
            response.sendRedirect(request.getContextPath() + "/viewblogs");
        } catch (SQLException ex) {
            Logger.getLogger(SubmitBlogServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String saveImage(Part part) {
        // Implement this method to save the image and return the image URL
        String fileName = part.getSubmittedFileName();
        String imageUrl = "/path/to/images/" + fileName; // Replace with actual path where images are saved
        // Save the image to the server here
        return imageUrl;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve all tags from the database
        BlogDAO blogDAO = new BlogDAO();
        List<Tag> tags = blogDAO.getAllTags(); // Get all tags
        request.setAttribute("tags", tags); // Set the tags in the request scope
        // Forward to the JSP to display the form
        request.getRequestDispatcher("addblog.jsp").forward(request, response);
    }
}
