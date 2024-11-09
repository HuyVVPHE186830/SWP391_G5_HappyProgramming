/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admin;

import dal.CourseDAO;
import dal.MentorPostDAO;
import dal.ParticipateDAO;
import dal.RequestDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Course;
import model.MentorPost;
import model.User;
import model.Course;
import model.Request;
import model.Participate;

/**
 *
 * @author mONESIUU
 */
public class MangeRequest extends HttpServlet {

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
    CourseDAO daoC = new CourseDAO();
    UserDAO daoU = new UserDAO();
    ParticipateDAO daoP = new ParticipateDAO();
    RequestDAO daoR = new RequestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        if ("RemoveMentor".equals(action)) {
            String menId = request.getParameter("mentorUsername");
            int couId = Integer.parseInt(request.getParameter("courseId"));
            removeMentor(couId, menId);
            response.sendRedirect("MangeRequest");
            return;
        }

        List<Participate> participateListByStatus = daoP.getParticipateByStatus(1);
        List<Participate> participateList = daoP.getAll();
        List<User> mentorList = daoC.getAllMentor();
        List<Course> courseList = daoC.getAllCourse();
        List<Request> requestWaitingList = daoR.getRequestByStatus(0);

        session.setAttribute("participateListByStatus", participateListByStatus);
        session.setAttribute("participateList", participateList);
        session.setAttribute("courseList", courseList);
        session.setAttribute("mentorList", mentorList);
        session.setAttribute("requestWaitingList", requestWaitingList);

        request.getRequestDispatcher("dashboard/mngrequest.jsp").forward(request, response);
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
        String menId = request.getParameter("mentorId");
        int couId = Integer.parseInt(request.getParameter("courseId"));
        String action = request.getParameter("action");
        switch (action) {
            case "Approve":
                daoP.changeParticipate(menId, couId, 1);
                daoR.changeRequest(menId, couId, 1);
                response.sendRedirect("MangeRequest");
                break;
            case "Reject":
                daoP.changeParticipate(menId, couId, 0);
                daoR.changeRequest(menId, couId, 0);
                response.sendRedirect("MangeRequest");
                break;
            default:
                throw new AssertionError();
        }
    }

    private void removeMentor(int couId, String menId) {
        daoR.deleteRequest2(couId, menId);
        daoP.deleteParticipate2(couId, menId);
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
