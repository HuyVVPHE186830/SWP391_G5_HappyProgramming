package controller;

import dal.BlogDAO;
import dal.TagDAO;
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

        // Initialize DAO
        TagDAO tagDAO = new TagDAO();

        // Split tags by comma and create a list
        List<Tag> tags = new ArrayList<>();
        if (tagsParam != null && !tagsParam.trim().isEmpty()) {
            String[] tagNames = tagsParam.split(",");
            for (String tagName : tagNames) {
                String trimmedTagName = tagName.trim();
                
                // Use getOrAddTag to retrieve existing tag or add a new one
                int tagId = tagDAO.getOrAddTag(trimmedTagName);
                if (tagId > 0) {
                    Tag tag = new Tag(tagId, trimmedTagName);
                    tags.add(tag); // Add the tag to the list
                }
            }
        }

        // Handle image uploads
        List<String> imageUrls = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getName().equals("blogImages") && part.getSize() > 0) {
                String imageUrl = saveImage(part);
                imageUrls.add(imageUrl);
            }
        }

        // Retrieve the creator's information from the session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String createdBy = user != null ? user.getUsername() : "defaultUsername";

        // Create a new Blog object
        Blog newBlog = new Blog(0, title, content, createdBy, imageUrls, tags);

        // Save the new blog using BlogDAO
        BlogDAO blogDAO = new BlogDAO();
        blogDAO.addBlog(newBlog);

        // Redirect to the blog list after submission
        response.sendRedirect(request.getContextPath() + "/viewblogs");
    }

    private String saveImage(Part part) {
        // Implement this method to save the image and return the image URL
        String fileName = part.getSubmittedFileName();
        String imageUrl = "/path/to/images/" + fileName; // Replace with the actual path where images are saved
        // Logic to save the image file goes here (e.g., save to server)
        return imageUrl;
    }
}
