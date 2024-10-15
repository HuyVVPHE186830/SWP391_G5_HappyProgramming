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

        // Split tags by comma
        List<Integer> tagIds = new ArrayList<>(); // List of tag IDs
        if (tagsParam != null && !tagsParam.trim().isEmpty()) {
            String[] tagNames = tagsParam.split(",");
            BlogDAO blogDAO = new BlogDAO();
            for (String tagName : tagNames) {
                tagName = tagName.trim(); // Clean up any extra spaces
                int tagId = blogDAO.getTagIdByName(tagName); // Search tag by name, assume it returns 0 if not found
                if (tagId > 0) {
                    tagIds.add(tagId); // Add the tag ID if found
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

        // Retrieve User from session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String createdBy = user != null ? user.getUsername() : "defaultUsername";

        // Create Blog object and add it to the database
        Blog newBlog = new Blog(0, title, content, createdBy, imageUrls, new ArrayList<>());

        BlogDAO blogDAO = new BlogDAO();
        blogDAO.addBlogWithTags(newBlog, tagIds);
        response.sendRedirect(request.getContextPath() + "/viewblogs");
    }

    // Handle getting the list of tags
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        List<Tag> tags = blogDAO.getAllTags();
        request.setAttribute("tags", tags);
        request.getRequestDispatcher("addblog.jsp").forward(request, response);
    }

    private String saveImage(Part part) {
        String fileName = part.getSubmittedFileName();
        String imageUrl = "img/" + fileName; // Replace with actual path
        return imageUrl;
    }
}
