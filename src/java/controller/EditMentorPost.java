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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.MentorPost;

/**
 *
 * @author Huy Võ
 *
 */
@MultipartConfig
public class EditMentorPost extends HttpServlet {

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
            out.println("<title>Servlet editMentorPost</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editMentorPost at " + request.getContextPath() + "</h1>");
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
        String courseIdStr = request.getParameter("courseId");
        int courseId = Integer.parseInt(courseIdStr);
        String mentorName = request.getParameter("mentorName");
        String postIdStr = request.getParameter("postId");
        int postId = Integer.parseInt(postIdStr);
        String title = request.getParameter("editTitle");
        String content = request.getParameter("editContent");
        String type = request.getParameter("editType");
        int postTypeId = mentorPostDAO.getPostTypeId(type);
        String deadlineStr = request.getParameter("editDeadline");

        String fileName = null;
        String fileType = null;
        byte[] fileContent = null;
        Part filePart = request.getPart("addFile");
        String oldFileContentStr = request.getParameter("oldFileContent");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName1 = filePart.getSubmittedFileName();
            String fileType1 = filePart.getContentType();
            InputStream inputStream = filePart.getInputStream();
            fileContent = inputStream.readAllBytes();
            fileName = fileName1;
            fileType = fileType1;
        } else {
            fileName = request.getParameter("oldFileName");
            fileType = request.getParameter("oldFileType");
            if (fileName.trim().isEmpty() && fileType.trim().isEmpty()) {
                fileContent = null;
            } else {
                fileContent = oldFileContentStr.getBytes();
            }
        }
        Timestamp deadline = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = dateFormat.parse(deadlineStr);
            deadline = new Timestamp(date.getTime());
        } catch (ParseException e) {
            System.out.println("Lỗi khi phân tích chuỗi ngày giờ: " + e.getMessage());
        }
        Timestamp time = new Timestamp(System.currentTimeMillis());
        MentorPost mp = new MentorPost(title, content, postTypeId, deadline, time, fileContent, fileName, fileType);
        mentorPostDAO.updateMentorPost(mp, postId);
        String success = "Edit successful!";
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
