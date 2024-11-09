package controller;

import dal.BlogCommentDAO;
import dal.ReportDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Report;
import model.User;
import java.sql.Timestamp;
import model.BlogComment;
import model.ReportType;

@WebServlet(name = "ReportComment", urlPatterns = {"/reportComment"})
public class ReportComment extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReportComment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReportComment at " + request.getContextPath() + "</h1>");
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
        int commentId = Integer.parseInt(request.getParameter("commentId"));
        int reportTypeId = Integer.parseInt(request.getParameter("reportTypeId"));
        String reportContent = request.getParameter("reportContent");
        User user = (User) request.getSession().getAttribute("user");
        int blogId = Integer.parseInt(request.getParameter("blogId"));

        BlogCommentDAO blogCommentDAO = new BlogCommentDAO();
        BlogComment blogComment = blogCommentDAO.getCommentById(commentId);

        ReportDAO reportDAO = new ReportDAO();
        if (reportDAO.hasUserReportedComment(commentId, user.getUsername())) {
            request.getSession().setAttribute("failedMsg", "You have reported this comment before.");
            response.sendRedirect("viewBlogDetail?id=" + blogId);
            return;
        }
        ReportType reportType = reportDAO.getReportTypeById(reportTypeId);

        Report report = new Report();
        report.setComment(blogComment);
        report.setUser(user);
        report.setReportTime(new Timestamp(System.currentTimeMillis()));
        report.setReportType(reportType);
        report.setReportContent(reportContent);

        boolean f = reportDAO.addReport(report);
        if (f) {
            request.getSession().setAttribute("succMsg", "Report comment successfully!");
        } else {
            request.getSession().setAttribute("failedMsg", "Something wrong on server...");
        }

        response.sendRedirect("viewBlogDetail?id=" + blogId);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
