
package controller;

import dal.BlogCommentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Blog;
import model.BlogComment;

@WebServlet(name="DeleteBlogComment", urlPatterns={"/deleteBlogComment"})
public class DeleteBlogComment extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeleteBlogComment</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteBlogComment at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int commentId = Integer.parseInt(request.getParameter("id"));
        
        BlogCommentDAO dao = new BlogCommentDAO();
        BlogComment comment = dao.getCommentById(commentId);
        Blog blog = comment.getBlog();
        int blogId = blog.getBlogId();
        
        List<BlogComment> replies = dao.getRepliesForComment(commentId);
        if (!replies.isEmpty()) {
            dao.deleteReplies(commentId);
        }
        
        boolean f = dao.deleteComment(commentId);
        if (f) {
            request.getSession().setAttribute("succMsg", "Delete comment successfully!");
        } else {
            request.getSession().setAttribute("failedMsg", "Something wrong on server...");
        }
        
        response.sendRedirect("viewBlogDetail?id=" + blogId);
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
