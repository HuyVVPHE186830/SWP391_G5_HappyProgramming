
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ConversationDAO;
import dal.CourseDAO;
import dal.MessageDAO;
import dal.RatingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.List;
import model.Course;

/**
 *
 * @author mONESIUU
 */
public class ManageConversation extends HttpServlet {

    ConversationDAO conversationDAO = new ConversationDAO();
    CourseDAO courseDAO = new CourseDAO();
    MessageDAO messageDAO = new MessageDAO();
    RatingDAO rateDao = new RatingDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String RatedUser = request.getParameter("recipientUsername");
        List<Course> listCourse4 = courseDAO.getAll();
        request.getSession().setAttribute("listCourse4", listCourse4);
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String conversationIdPr = request.getParameter("conversationId");
        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        switch (action) {
            case "edit-conversation":
                editConversation(request);
                break;
            case "delete-conversation":
                deleteConversation(request);
                break;
            case "delete-message":
                deleteMessage(request);
                break;
            case "edit-message":
                editMessage(request);
                break;
            case "rate-recipient":
                rateRecipient(request);
                break;
            case "rate-recipient2":
                rateRecipient2(request, response);
                break;

            default:

        }
        response.sendRedirect("sendMessage?conversationId=" + conversationIdPr);

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

    private void editConversation(HttpServletRequest request) {
        try {
            int conversationIdParam = Integer.parseInt(request.getParameter("conversationId"));
            String newConversationName = request.getParameter("newConversationName");
            conversationDAO.editConversationNameById(conversationIdParam, newConversationName);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

    }

    private void deleteConversation(HttpServletRequest request) {
        try {
            int conversationIdParam = Integer.parseInt(request.getParameter("conversationId"));
            conversationDAO.deleteConversationNameById(conversationIdParam);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

    }

    private void deleteMessage(HttpServletRequest request) {
        try {
            int messageIdParam = Integer.parseInt(request.getParameter("messageId"));
            conversationDAO.deleteMessageById(messageIdParam);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void editMessage(HttpServletRequest request) {
        try {
            int messageIdParam = Integer.parseInt(request.getParameter("messageId"));
            String newMsgContenT = request.getParameter("newMsgContent");
            conversationDAO.editMessageById(messageIdParam, newMsgContenT);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void rateRecipient(HttpServletRequest request) {
        String RatedUser = request.getParameter("recipientUsername");
        String CurrentUser = request.getParameter("currentUser");
        int Star = Integer.parseInt(request.getParameter("rating"));
        int CourseID = Integer.parseInt(request.getParameter("courseId"));

        String RatedContent = request.getParameter("comments");

        rateDao.addFeedback(CurrentUser, RatedUser, Star, CourseID, RatedContent);

    }

    private void rateRecipient2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String RatedUser = request.getParameter("recipientUsername");
        String CurrentUser = request.getParameter("currentUser");
        int Star = Integer.parseInt(request.getParameter("rating"));
        int CourseID = Integer.parseInt(request.getParameter("courseId"));
        String RatedContent = request.getParameter("comments");

        // Tạo một chuỗi để hiển thị
        String olalla = "Test: " + CurrentUser + ", " + RatedUser + ", " + Star + ", " + CourseID + ", " + RatedContent;
        System.out.println(olalla);

        // Thiết lập thuộc tính để chuyển đến JSP
        request.setAttribute("olalla", olalla);

        // Chuyển hướng đến một trang JSP để hiển thị kết quả
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }

}
