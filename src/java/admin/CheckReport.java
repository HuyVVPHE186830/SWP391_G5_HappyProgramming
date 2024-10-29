
package admin;

import dal.BlogCommentDAO;
import dal.ReportDAO;
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

@WebServlet(name="CheckReport", urlPatterns={"/checkReport"})
public class CheckReport extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckReport</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckReport at " + request.getContextPath () + "</h1>");
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
        
        List<BlogComment> replies = dao.getRepliesForComment(commentId);
        if (!replies.isEmpty()) {
            dao.deleteReplies(commentId);
        }
        
        ReportDAO reportDAO = new ReportDAO();
        reportDAO.deleteReportByCommentId(commentId);
        
        boolean f = dao.deleteComment(commentId);
        if (f) {
            request.getSession().setAttribute("succMsg", "Report accepted!");
        } else {
            request.getSession().setAttribute("failedMsg", "Something wrong on server...");
        }
        
        response.sendRedirect("ManageReport");
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int commentId = Integer.parseInt(request.getParameter("id"));
        String user = request.getParameter("user");
        
        ReportDAO reportDAO = new ReportDAO();
        boolean f = reportDAO.deleteReport(commentId, user);
        if (f) {
            request.getSession().setAttribute("rejectMsg", "Report rejected!");
        } else {
            request.getSession().setAttribute("failedMsg", "Something wrong on server...");
        }
        
        response.sendRedirect("ManageReport");
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
