/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MentorPostDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import model.MentorPostComment;
import model.User;

/**
 *
 * @author Huy Võ
 */
public class ManageCourseComment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet manageCourseComment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet manageCourseComment at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdString = request.getParameter("postId");
        String courseIdString = request.getParameter("courseId");
        int courseId = Integer.parseInt(courseIdString);
        String username = request.getParameter("username");
        String mentorName = request.getParameter("mentorName");
        String commentContent = request.getParameter("commentContent");
        UserDAO daoU = new UserDAO();

        if (postIdString != null && commentContent != null && !commentContent.trim().isEmpty()) {
            try {
                int postId = Integer.parseInt(postIdString);
                Timestamp commentedAt = new Timestamp(System.currentTimeMillis());
                User user = daoU.getUserByUsernameM(username);

                MentorPostComment comment = new MentorPostComment();
                comment.setPostId(postId);
                comment.setCommentedBy(username);
                comment.setCommentedAt(commentedAt);
                comment.setCommentContent(commentContent);

                MentorPostDAO mentorPostDAO = new MentorPostDAO();
                mentorPostDAO.addComment(comment);

                StringBuilder commentHtml = new StringBuilder();
                commentHtml.append("<div class='comment d-flex align-items-start mb-3' style='margin-bottom: 5px !important;'>")
                        .append("<img src='data:image/jpeg;base64,") // Bạn cần thay thế bằng cách lấy ảnh người dùng
                        .append(user.getAvatarPath()).append("' alt='Avatar' class='avatar-image' style='width:40px; height:40px; border-radius:50%; object-fit: cover;'>")
                        .append("<div class='comment-body' style='background-color: #f1f1f1; margin-left:10px; padding: 10px; border-radius: 5px;'>")
                        .append("<div class='comment-author-info d-flex justify-content-between align-items-center'>")
                        .append("<p class='comment-author fw-bold mb-1' style='font-weight: bold; margin-bottom: 0;'>")
                        .append(user.getLastName()).append(" ").append(user.getFirstName()).append("</p>") // Tên và họ của người dùng
                        .append("</div>")
                        .append("<p class='comment-text' style='margin-bottom: 0'>")
                        .append(commentContent).append("</p>") // Nội dung bình luận
                        .append("</div></div>") // Đóng comment-body và comment
                        .append("<p style='font-size: 0.8em; color: gray; margin: 0 50px 10px;'>")
                        .append(new SimpleDateFormat("dd-MM-yyyy, HH:mm").format(commentedAt)) // Thời gian bình luận
                        .append("</p>");

                response.setContentType("text/html");
                response.getWriter().write(commentHtml.toString());

            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("manageCourse?courseId=" + courseId + "&mentorName=" + mentorName);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
