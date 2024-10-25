
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import service.FileConverter;
import service.ImageConverter;

/**
 *
 * @author Huy Võ
 */
@MultipartConfig
public class Register extends HttpServlet {

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
            out.println("<title>Servlet register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet register at " + request.getContextPath() + "</h1>");
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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String dobString = request.getParameter("dob");
        Date dob = null;

        // Parse date
        if (dobString != null && !dobString.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dob = sdf.parse(dobString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        Path avatarPath = Paths.get(getServletContext().getRealPath("/img/bc439871417621836a0eeea768d60944.jpg"));
        byte[] avatarBytes = Files.readAllBytes(avatarPath);
        String avatar = ImageConverter.gI().encode(avatarBytes);

        String cvBase64 = null;

        if ("mentor".equals(request.getParameter("role"))) {
            Part cvPart = request.getPart("cv");
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

        if (!password.equals(repassword)) {
            session.setAttribute("error", "*Passwords do not match");
            response.sendRedirect("register.jsp");
            return;
        }

        for (User u : users) {
            if (username.equals(u.getUsername())) {
                redString += "*Username has been used<br>";
            }
            if (email.equals(u.getMail())) {
                redString += "*Email has been used<br>";
            }
        }
        if (!redString.isEmpty()) {
            session.setAttribute("error", redString);
        } else {
            Date doc = new Date();
            int roleId = role.equals("mentor") ? 2 : 3;
            User user = new User(username, password, firstName, lastName, dob, email, doc, avatar, cvBase64, true, false, roleId);
            dao.registerUser(user);
            greenString = "Register Successfully!";
            session.setAttribute("note", greenString);
        }
        response.sendRedirect("register.jsp");
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
