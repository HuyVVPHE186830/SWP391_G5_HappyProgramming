package admin;

import dal.UserDAO;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddAccountControl", urlPatterns = {"/addaccount"})
public class AddAccountControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
  
        
        
        
        
        // Retrieve form parameters from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = dateFormat.parse(request.getParameter("dob")); // Date of birth
        Date createdDate = new Date(); // Current date as the account creation date

        String email = request.getParameter("email");
        String avatarPath = request.getParameter("avatarPath");
        String cvPath = request.getParameter("cvPath");

        boolean activeStatus = request.getParameter("activeStatus") != null; // Checkbox, true if checked
        boolean isVerified = request.getParameter("isVerified") != null; // Checkbox, true if checked

        String verificationCode = request.getParameter("verificationCode");

        // Role ID: Based on the selected role from the dropdown (1 for Admin, 2 for Mentor, 3 for Mentee)
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        String msg = "";
        UserDAO dao = new UserDAO();

        // Check if the username already exists
        boolean check = dao.checkUserNameDuplicate(username);
        if (check) {
            msg = "Username already exists!";
            request.setAttribute("error", msg);
        } else {
            // Insert new user into the database
            dao.insertUser(username, password, firstName, lastName, dob, email, createdDate, avatarPath, cvPath, activeStatus, isVerified, verificationCode, roleId);
            msg = "Username " + username + " added successfully!";
            request.setAttribute("mess", msg);
        }

        // Forward the request to the account management page
        request.getRequestDispatcher("managerAccount").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AddAccountControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AddAccountControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for adding new accounts";
    }
}
