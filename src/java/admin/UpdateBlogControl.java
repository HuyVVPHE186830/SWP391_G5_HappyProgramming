package admin;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MultipartConfig
public class UpdateBlogControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Retrieve form data
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String tagsInput = request.getParameter("tags");

        BlogDAO blogDAO = new BlogDAO();
        Blog blog = blogDAO.getBlogById(blogId);

        if (blog != null) {
            // Update title and content
            blog.setTitle(title);
            blog.setContent(content);

            // Handle tag management
            List<String> tagNames = Arrays.asList(tagsInput.split("\\s*,\\s*"));
            List<Tag> tags = new ArrayList<>();
            for (String tagName : tagNames) {
                int tagId = blogDAO.getTagIdByName(tagName);
                if (tagId == 0) { // If tag doesn't exist, add it to the database
                    tagId = blogDAO.addNewTag(tagName);
                }
                tags.add(new Tag(tagId, tagName));
            }
            blog.setTags(tags);

            // Handle image deletion
            String[] imagesToDelete = request.getParameterValues("deleteImages");
            if (imagesToDelete != null) {
                for (String imageUrl : imagesToDelete) {
                    blog.getImageUrls().remove(imageUrl);
                }
            }

            // Handle new image uploads
            for (Part part : request.getParts()) {
                if (part.getName().equals("images") && part.getSize() > 0) {
                    String fileName = part.getSubmittedFileName();
                    String imageUrl = "blogimg/" + fileName;
                    String uploadPath = getServletContext().getRealPath("/") + "blogimg" + File.separator + fileName;
                    part.write(uploadPath); // Save image file to server
                    blog.getImageUrls().add(imageUrl); // Add new image URL to blog
                }
            }

            // Update blog in database
            blogDAO.updateBlog(blog);
        }

        response.sendRedirect("managerBlog");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
