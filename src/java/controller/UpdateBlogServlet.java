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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.User;

@MultipartConfig
public class UpdateBlogServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve and validate the blog ID
        HttpSession session = request.getSession();
        int blogId = (int) session.getAttribute("blogId");
        
        // Retrieve form data
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String tagsInput = request.getParameter("tags");
        User u = (User) session.getAttribute("user");
        String username = u.getUsername();

        // Parse tags from the input string
        List<String> tagNames = Arrays.asList(tagsInput.split("\\s*,\\s*"));
        List<Tag> tags = new ArrayList<>();
        BlogDAO blogDAO = new BlogDAO();

        for (String tagName : tagNames) {
            int tagId = blogDAO.getTagIdByName(tagName);
            Tag tag = new Tag(tagId, tagName);
            tags.add(tag);
        }

        // Retrieve the existing blog and update its details
        Blog blog = blogDAO.getBlogById(blogId);
        if (blog != null && blog.getCreatedBy().equals(username)) {
            blog.setTitle(title);
            blog.setContent(content);
            blog.setTags(tags);
            blogDAO.updateBlog(blog); // Updates blog details including tags and images
        }
        
        PrintWriter out = response.getWriter();
        out.print(tagNames);
        out.print(tags);
        out.print(title + content + tagsInput + username);
        // Redirect to view the updated blog details
        response.sendRedirect("viewBlogDetail?id=" + blogId);
    }
}
