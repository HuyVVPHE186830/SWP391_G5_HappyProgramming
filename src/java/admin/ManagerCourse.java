package admin;

import dal.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Course;

@WebServlet(name = "ManagerCourse", urlPatterns = {"/ManagerCourse"})
public class ManagerCourse extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDAO dao = new CourseDAO();
        List<Course> list = dao.getAllCoursesForAdmin();
        HttpSession session = request.getSession();
        session.setAttribute("listCourses", list);
        request.getRequestDispatcher("dashboard/mngcourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("valueSearch");
        CourseDAO dao = new CourseDAO();
        List<Course> list = dao.getAllSearchCoursesForAdmin(key);
        HttpSession session = request.getSession();
        session.setAttribute("listCourses", list);
        request.getRequestDispatcher("dashboard/mngcourse.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
