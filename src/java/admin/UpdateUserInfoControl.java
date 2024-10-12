package admin;

import dal.UserDAO;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateUserInfoControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Retrieve form parameters from the request
        String username = request.getParameter("username");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String avatarPath = request.getParameter("avatarPath");
        String cvPath = request.getParameter("cvPath");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = dateFormat.parse(request.getParameter("dob")); // Date of birth

        boolean activeStatus = request.getParameter("activeStatus") != null; // Checkbox
        boolean isVerified = request.getParameter("isVerified") != null; // Checkbox

        // Role ID: Based on the selected role from the dropdown
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        UserDAO dao = new UserDAO();
        String msg;

        // Pass the `dob` as a `Date` to the DAO (since the DB should store it as a `Date`)
        boolean success = dao.updateUserInfo(username, firstName, lastName, dob, email, avatarPath, cvPath, activeStatus, isVerified, roleId);
        if (success) {
            msg = "User " + username + " updated successfully!";
            request.setAttribute("mess", msg);
        } else {
            msg = "Error updating user " + username + ".";
            request.setAttribute("error", msg);
        }
        
        HttpSession session = request.getSession();
        List<User> list = dao.getAll();
        session.setAttribute("listUsers", list);
        
        request.getRequestDispatcher("ManagerAccount").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateUserInfoControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(UpdateUserInfoControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating user information";
    }
}
