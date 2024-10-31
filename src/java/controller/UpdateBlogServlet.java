package controller;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MultipartConfig
public class UpdateBlogServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        
        // Retrieve form data
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String tagsInput = request.getParameter("tags");
        User u = (User) session.getAttribute("user");
        String username = u.getUsername();

        List<String> tagNames = Arrays.asList(tagsInput.split("\\s*,\\s*"));
        List<Tag> tags = new ArrayList<>();
        BlogDAO blogDAO = new BlogDAO();

        for (String tagName : tagNames) {
            int tagId = blogDAO.getTagIdByName(tagName);
            if (tagId == 0) { // If tag does not exist, add it to the database
                tagId = blogDAO.addNewTag(tagName);
            }
            tags.add(new Tag(tagId, tagName)); // Add tag to list with either existing or new tag ID
        }

        Blog blog = blogDAO.getBlogById(blogId);
        if (blog != null && blog.getCreatedBy().equals(username)) {
            blog.setTitle(title);
            blog.setContent(content);
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
                    part.write(uploadPath); // Save the image file to the server
                    blog.getImageUrls().add(imageUrl); // Add the new image URL to the blog
                }
            }

            blogDAO.updateBlog(blog);
        }

        response.sendRedirect("viewBlogDetail?id=" + blogId);
    }
}
