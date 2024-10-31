package controller;

import dal.BlogDAO;
import model.Blog;
import model.Tag;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve blog title, content, and tags
        String title = request.getParameter("blogTitle");
        String content = request.getParameter("blogContent");
        String tagsParam = request.getParameter("blogTags"); // Comma-separated input

        BlogDAO blogDAO = new BlogDAO();
        List<Integer> tagIds = new ArrayList<>(); // Store tag IDs

        if (tagsParam != null && !tagsParam.trim().isEmpty()) {
            String[] tagNames = tagsParam.split("\\s*,\\s*"); // Split and trim whitespace
            for (String tagName : tagNames) {
                tagName = tagName.trim();
                int tagId = blogDAO.getTagIdByName(tagName);
                if (tagId == 0) { // Tag not found in database
                    tagId = blogDAO.addNewTag(tagName); // Add new tag and get its ID
                }
                tagIds.add(tagId); // Add tag ID to list
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
        blogDAO.addBlogWithTags(newBlog, tagIds); // Add blog with tags to DB

        // Redirect to the blog listing page
        response.sendRedirect(request.getContextPath() + "/viewblogs");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        List<Tag> tags = blogDAO.getAllTags();
        request.setAttribute("tags", tags);
        request.getRequestDispatcher("addblog.jsp").forward(request, response);
    }

    private String saveImage(Part part) {
        String fileName = part.getSubmittedFileName();
        String imageUrl = "blogimg/" + fileName; // Replace with actual path
        return imageUrl;
    }
}
