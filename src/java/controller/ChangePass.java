package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

public class ChangePass extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet changePass</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changePass at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int id = Integer.parseInt(request.getParameter("id"));
            String curPass = request.getParameter("curPass");
            String newPass = request.getParameter("newPass");
            String confirmPass = request.getParameter("confirmPass");

            User u = new User();
            u.setId(id);
            u.setPassword(newPass);

            UserDAO dao = new UserDAO();
            boolean f = dao.checkPassword(id, curPass);
            if (f) {
                boolean f2 = dao.changePass(u);
                if (f2) {
                    User updatedUser = dao.getUserById(id);
                    session.setAttribute("user", updatedUser);
                    session.setAttribute("succMsg", "Change password successfully!");
                    response.sendRedirect("changePass.jsp");
                } else {
                    session.setAttribute("failedMsg", "Something wrong on server...");
                    response.sendRedirect("changePass.jsp");
                }
            } else {
                session.setAttribute("failedMsg", "Current password is incorrect!");
                response.sendRedirect("changePass.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
