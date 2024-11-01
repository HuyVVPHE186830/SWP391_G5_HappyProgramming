package admin;

import dal.BlogDAO;
import model.Blog;
import model.Tag;
import model.User;

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

@MultipartConfig
public class AddBlogControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve blog title, content, and tags
        String title = request.getParameter("blogTitle");
        String content = request.getParameter("blogContent");
        String tagsParam = request.getParameter("blogTags");

        BlogDAO blogDAO = new BlogDAO();
        List<Integer> tagIds = new ArrayList<>();

        // Process tags
        if (tagsParam != null && !tagsParam.trim().isEmpty()) {
            String[] tagNames = tagsParam.split("\\s*,\\s*");
            for (String tagName : tagNames) {
                tagName = tagName.trim();
                int tagId = blogDAO.getTagIdByName(tagName);
                if (tagId == 0) {
                    tagId = blogDAO.addNewTag(tagName);
                }
                tagIds.add(tagId);
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

        // Retrieve admin User from session
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");
        String createdBy = adminUser != null ? adminUser.getUsername() : "admin";

        // Create Blog object and add it to the database
        Blog newBlog = new Blog(0, title, content, createdBy, imageUrls, new ArrayList<>());
        blogDAO.addBlogWithTags(newBlog, tagIds);

        // Redirect to the blog management page
        response.sendRedirect(request.getContextPath() + "/manageblogs");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        List<Tag> tags = blogDAO.getAllTags();
        request.setAttribute("tags", tags);
        request.getRequestDispatcher("admin/addblog.jsp").forward(request, response);
    }

    private String saveImage(Part part) {
        String fileName = part.getSubmittedFileName();
        String imageUrl = "blogimg/" + fileName; // Set the path to save image
        return imageUrl;
    }
}
