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
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
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
        MentorPostDAO daoM = new MentorPostDAO();
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        Submission submission = daoM.getSubmissionBySubmissionId(submissionId);
        if (submission != null && submission.getSubmissionContent() != null) {
            // Thiết lập kiểu dữ liệu trả về là file đính kèm
            response.setContentType(submission.getFileType()); // Sử dụng loại file đã lưu
            response.setHeader("Content-Disposition", "attachment;filename=" + submission.getFileName());

            // Ghi nội dung file vào output stream
            response.getOutputStream().write(submission.getSubmissionContent());
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
        HttpSession session = request.getSession(true);
        int postId = Integer.parseInt(request.getParameter("postId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String mentorName = request.getParameter("mentorName");
        String submittedBy = request.getParameter("username");
        String deadlineParam = request.getParameter("deadline");
        Timestamp deadlineTimestamp = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date deadlineDate = dateFormat.parse(deadlineParam);
            deadlineTimestamp = new Timestamp(deadlineDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean isLate = System.currentTimeMillis() > (deadlineTimestamp != null ? deadlineTimestamp.getTime() : 0);
        boolean status = true;
        Timestamp submittedAt = new Timestamp(System.currentTimeMillis());
        Part filePart = request.getPart("file");
        byte[] submissionContent = null;
        Submission submission = new Submission();
        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            String fileType = filePart.getContentType();
            InputStream inputStream = filePart.getInputStream();
            submissionContent = inputStream.readAllBytes();
            submission.setFileName(fileName);
            submission.setFileType(fileType);
        }
        submission.setPostId(postId);
        submission.setSubmittedBy(submittedBy);
        submission.setSubmittedAt(submittedAt);
        submission.setSubmissionContent(submissionContent);
        submission.setLate(isLate);

        MentorPostDAO daoM = new MentorPostDAO();
        daoM.addOrUpdateSubmission(submission);
        String success = "Submit successful!";
        session.setAttribute("success", success);

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
