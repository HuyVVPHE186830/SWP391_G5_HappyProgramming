package controller;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.User;

public class EditBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("id"));
        BlogDAO blogDAO = new BlogDAO();
        Blog blog = blogDAO.getBlogById(blogId);

        request.setAttribute("blog", blog);
        request.getRequestDispatcher("edit_blog_form.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String createdBy = ((User) request.getSession().getAttribute("user")).getUsername();

        Blog blog = new Blog(blogId, title, content, createdBy, /*images*/ null, /*tags*/ null);
        BlogDAO blogDAO = new BlogDAO();
        blogDAO.updateBlog(blog);

        response.sendRedirect("viewBlogDetail?id=" + blogId);
    }
}
