/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package admin;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.User;

/**
 *
 * @author Sapphire
 */
public class DeleteUserInfoControl extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");

        // Retrieve the username from the request
        String username = request.getParameter("username");

        UserDAO dao = new UserDAO();
        String msg = null;

        boolean check = dao.checkUserDeactivated(username);
        if (check) {
            msg = "This user is already deactivated!";
            request.setAttribute("error", msg);

        } else {
            boolean success = dao.deactivateUser(username);
            if (success) {
                // Call the DAO method to update the active status to 0 (inactive)
                msg = "User " + username + " has been deactivated successfully!";
                request.setAttribute("mess", msg);

            } else {
                msg = "Error deactivating user " + username + ".";
                request.setAttribute("error", msg);
            }
        }
        HttpSession session = request.getSession();
        List<User> list = dao.getAll();
        session.setAttribute("listUsers", list);

        request.getRequestDispatcher("ManagerAccount").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
