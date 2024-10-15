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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.User;

@MultipartConfig
public class SubmitBlogServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); // Retrieve User object
        String createdBy = user != null ? user.getUsername() : "defaultUsername"; // Replace with actual username logic

        Blog newBlog = new Blog(0, title, content, createdBy, imageUrls, tags);

        // Create a new Blog object and save it using BlogDAO
        BlogDAO blogDAO = new BlogDAO();
        blogDAO.addBlog(newBlog); // Method to insert the new blog into the database

        // Redirect to the blog list after submission
        response.sendRedirect(request.getContextPath() + "/viewblogs");
    }

    private String saveImage(Part part) {
        // Implement this method to save the image and return the image URL
        // This is a placeholder implementation. You should implement actual file saving logic.
        String fileName = part.getSubmittedFileName();
        String imageUrl = "C:\\Users\\Sapphire\\OneDrive - MSFT\\Pictures\\Screenshots" + fileName; // Replace with actual path where images are saved
        // Save the image to the server here
        return imageUrl;
    }
}
