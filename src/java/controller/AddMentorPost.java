/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.MentorPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import model.MentorPost;
import model.Submission;

/**
 *
 * @author Huy Võ
 */
@MultipartConfig
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
        MentorPostDAO daoM = new MentorPostDAO();
        int postId = Integer.parseInt(request.getParameter("postId"));
        MentorPost post = daoM.getPostById(postId);
        if (post != null && post.getFileContent() != null) {
            response.setContentType(post.getFileType());
            response.setHeader("Content-Disposition", "attachment;filename=" + post.getFileName());
            response.getOutputStream().write(post.getFileContent());
        } else {
            response.getWriter().println("File not found or submission is empty.");
        }
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

        Timestamp deadline = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = dateFormat.parse(deadlineStr);
            deadline = new Timestamp(date.getTime());
        } catch (ParseException e) {
            System.out.println("Lỗi khi phân tích chuỗi ngày giờ: " + e.getMessage());
        }
        Part filePart = request.getPart("addFile");
        MentorPost mentorPost = new MentorPost();
        byte[] fileContent = null;
        if (filePart != null && filePart.getSize() > 0) {
            // Có file được tải lên
            String fileName = filePart.getSubmittedFileName();
            String fileType = filePart.getContentType();
            InputStream inputStream = filePart.getInputStream();
            fileContent = inputStream.readAllBytes();

            // Đặt thông tin file cho đối tượng mentorPost
            mentorPost.setFileName(fileName);
            mentorPost.setFileType(fileType);
        }
        mentorPost.setFileContent(fileContent);
        mentorPost.setPostTitle(postTitle);
        mentorPost.setPostContent(postContent);
        mentorPost.setPostTypeId(postTypeId);
        mentorPost.setDeadline(deadline);
        mentorPost.setCourseId(courseId);
        mentorPost.setCreatedBy(createdBy);
        mentorPost.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        mentorPostDAO.addMentorPost(mentorPost);
        session.setAttribute("success", "Create post successful!");
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
