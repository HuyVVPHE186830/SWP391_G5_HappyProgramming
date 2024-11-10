
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.User;
import service.FileConverter;
import service.ImageConverter;

/**
 *
 * @author Huy Võ
 */
@MultipartConfig
public class UserProfile extends HttpServlet {

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
            out.println("<title>Servlet userProfile</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet userProfile at " + request.getContextPath() + "</h1>");
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
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("userProfile.jsp").forward(request, response);
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
        User user = (User) session.getAttribute("user");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("usernameHidden");
        String dobString = request.getParameter("dob");
        Date dob = null;
        int role = Integer.parseInt(request.getParameter("role"));
        if (dobString != null && !dobString.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dob = sdf.parse(dobString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String newEmail = request.getParameter("email");
        String oldEmail = request.getParameter("oldEmail");

        String cvBase64 = null;

        if (role == 2) {
            Part cvPart = request.getPart("cvFileInput");
            if (cvPart != null && cvPart.getSize() > 0) {
                FileConverter fileConverter = FileConverter.getInstance();
                String cvPrefix = "cvFile";
                File tempFile = File.createTempFile(cvPrefix, ".pdf");
                cvPart.write(tempFile.getAbsolutePath());
                cvBase64 = fileConverter.encode(tempFile);
                tempFile.delete();
            }
        }

        UserDAO dao = new UserDAO();
        List<User> users = dao.getAll();
        String redString = "";
        String greenString = "";
        if (!newEmail.equals(oldEmail)) {
            for (User u : users) {
                if (newEmail.equals(u.getMail())) {
                    redString += "Email has been used";
                }
            }

        }
        if (!redString.isEmpty()) {
            session.setAttribute("error", redString);
            response.sendRedirect("editUser.jsp");
        } else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setDob(dob);
            user.setMail(newEmail);
            user.setCvPath(cvBase64);
            dao.updateProfile(username, user);
            greenString = "Update Successfully!";
            session.setAttribute("success", greenString);
            response.sendRedirect("userProfile.jsp");
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
