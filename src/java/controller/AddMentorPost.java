/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MentorPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import model.MentorPost;

/**
 *
 * @author Huy Võ
 */
public class AddMentorPost extends HttpServlet {

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
            out.println("<title>Servlet addMentorPost</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addMentorPost at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        MentorPostDAO mentorPostDAO = new MentorPostDAO();
        String postTitle = request.getParameter("addTitle");
        String postContent = request.getParameter("addContent");
        String postType = request.getParameter("addType");
        int postTypeId = mentorPostDAO.getPostTypeId(postType);
        
        String deadlineStr = request.getParameter("deadline");
        String courseIdStr = request.getParameter("courseId");
        String mentorName = request.getParameter("mentorName");
        int courseId = Integer.parseInt(courseIdStr);
        String createdBy = request.getParameter("username");

        // Khai báo biến deadline
        Timestamp deadline = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        // Chuyển đổi deadline từ String sang java.sql.Timestamp
        try {
            // Phân tích chuỗi thành đối tượng Date
            Date date = dateFormat.parse(deadlineStr);
            // Chuyển đổi Date thành Timestamp
            deadline = new Timestamp(date.getTime());
        } catch (ParseException e) {
            System.out.println("Lỗi khi phân tích chuỗi ngày giờ: " + e.getMessage());
        }

        // Tạo đối tượng MentorPost
        MentorPost mentorPost = new MentorPost();
        mentorPost.setPostTitle(postTitle);
        mentorPost.setPostContent(postContent);
        mentorPost.setPostTypeId(postTypeId);
        mentorPost.setDeadline(deadline);
        mentorPost.setCourseId(courseId);
        mentorPost.setCreatedBy(createdBy);
        mentorPost.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Đặt createdAt là thời gian hiện tại
        mentorPostDAO.addMentorPost(mentorPost);

        response.sendRedirect("manageCourse?courseId=" + courseId + "&mentorName=" + mentorName);
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
