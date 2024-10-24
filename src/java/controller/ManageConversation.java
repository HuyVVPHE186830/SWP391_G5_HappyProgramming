
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ConversationDAO;
import dal.MessageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

/**
 *
 * @author mONESIUU
 */
public class ManageConversation extends HttpServlet {

    ConversationDAO conversationDAO = new ConversationDAO();
    MessageDAO messageDAO = new MessageDAO();

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
        // get session
        HttpSession session = request.getSession();
        // get action
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

}
