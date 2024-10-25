
package controller;

import dal.BlogCommentDAO;
import dal.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import model.Blog;
import model.BlogComment;
import model.User;

@WebServlet(name="EditBlogComment", urlPatterns={"/editBlogComment"})
public class EditBlogComment extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditBlogComment</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditBlogComment at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String commentContent = request.getParameter("commentContent");
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        int commentId = Integer.parseInt(request.getParameter("commentId"));

        // Retrieve the user (assuming the user is logged in)
        User user = (User) request.getSession().getAttribute("user");

        BlogCommentDAO blogCommentDAO = new BlogCommentDAO();
        blogCommentDAO.editComment(commentId, commentContent);

        // Redirect back to the blog details page
        response.sendRedirect("viewBlogDetail?id=" + blogId);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
