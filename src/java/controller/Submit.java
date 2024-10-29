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
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Submission;

/**
 *
 * @author Huy Võ
 */
@MultipartConfig
public class Submit extends HttpServlet {

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
            out.println("<title>Servlet Submit</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Submit at " + request.getContextPath() + "</h1>");
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
        int postId = Integer.parseInt(request.getParameter("postId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String mentorName = request.getParameter("mentorName");
        String submittedBy = request.getParameter("username");
        String deadlineParam = request.getParameter("deadline");
        Timestamp deadlineTimestamp = null;

        try {
            // Chuyển đổi chuỗi ngày giờ thành Timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date deadlineDate = dateFormat.parse(deadlineParam);
            deadlineTimestamp = new Timestamp(deadlineDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu không thể phân tích deadline
        }

        boolean isLate = System.currentTimeMillis() > (deadlineTimestamp != null ? deadlineTimestamp.getTime() : 0);
        boolean status = true;
        Timestamp submittedAt = new Timestamp(System.currentTimeMillis());
        Part filePart = request.getPart("file");
        String submissionContent = null;

        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            submissionContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }

        Submission submission = new Submission();
        submission.setPostId(postId);
        submission.setSubmittedBy(submittedBy);
        submission.setSubmittedAt(submittedAt);
        submission.setSubmissionContent(submissionContent);
        submission.setLate(isLate);
        submission.setStatus(status);

        MentorPostDAO daoM = new MentorPostDAO();
        daoM.addSubmission(submission);

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
