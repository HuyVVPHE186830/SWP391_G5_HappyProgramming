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
import java.util.Date;
import java.util.List;
import model.Course;

@WebServlet(name = "UpdateCourse", urlPatterns = {"/updatecourse"})
public class UpdateCourse extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updatecourse</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updatecourse at " + request.getContextPath() + "</h1>");
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
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            String courseName = request.getParameter("courseName");
            String description = request.getParameter("description");
            String[] categoryIds = request.getParameterValues("categoryIds");
            Course c = new Course();
            c.setCourseId(courseId);
            c.setCourseName(courseName);
            c.setCourseDescription(description);
            CourseDAO dao = new CourseDAO();
            if (dao.isCourseNameDuplicate(courseId, courseName)) {
                session.setAttribute("failedMsg", "Course name already exists!");
            } else {
                boolean f = dao.updateCourse(c);
                if (f) {
                    if (categoryIds != null) {
                        dao.deleteCourseCategory(courseId);
                        for (String categoryId : categoryIds) {
                            dao.addCourseCategory(Integer.parseInt(categoryId), courseId);
                        }
                    }

                    session.setAttribute("succMsg", "Update course successfully!");
                } else {
                    session.setAttribute("failedMsg", "Something wrong on server...");
                }
            }
            List<Course> list = dao.getAllCoursesForAdmin();
            session.setAttribute("listCourses", list);
            request.getRequestDispatcher("dashboard/mngcourse.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
