package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import dal.CourseDAO;
import dal.RequestDAO;
import dal.StatusDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import model.Course;
import model.Request;
import model.Status;
import model.User;

/**
 *
 * @author Admin
 */
public class ListRequestForMentor extends HttpServlet {

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
            out.println("<title>Servlet ListRequestForMentor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListRequestForMentor at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        String userId_str = request.getParameter("userId");
        RequestDAO daoR = new RequestDAO();
        UserDAO daoU = new UserDAO();
        CourseDAO daoC = new CourseDAO();
        StatusDAO daoS = new StatusDAO();
        try {
            int userId = Integer.parseInt(userId_str);
            User user = daoU.getUserById(userId);
            List<Request> requests = daoR.getAllRequestByUsernameForList(user.getUsername());
            List<Course> courses = daoC.getAll();
            List<Status> status = daoS.getAll();
            request.setAttribute("requests", requests);
            request.setAttribute("courses", courses);
            request.setAttribute("status", status);
            request.getRequestDispatcher("listRequestForMentor.jsp").forward(request, response);
        } catch (Exception e) {
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
        User u = (User) session.getAttribute("user");
        String keyword = request.getParameter("keyword");
        RequestDAO daoR = new RequestDAO();
        CourseDAO daoC = new CourseDAO();
        StatusDAO daoS = new StatusDAO();
        List<Request> requests = daoR.getAllRequestOfMentorByKeyword(keyword ,u.getUsername());
        List<Course> courses = daoC.getAll();
        List<Status> status = daoS.getAll();
        request.setAttribute("requests", requests);
        request.setAttribute("courses", courses);
        request.setAttribute("status", status);
        request.getRequestDispatcher("listRequestForMentor.jsp").forward(request, response);
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
