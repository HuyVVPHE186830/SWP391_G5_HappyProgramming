package controller;

import dal.CourseDAO;
import dal.ParticipateDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Course;
import model.Course_Category;
import model.Participate;
import model.User;
import util.PageControl;

public class ViewMyCourses extends HttpServlet {
    
    private final CourseDAO courseDAO = new CourseDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userName = user.getUsername();

        String keyword = request.getParameter("keyword");
        String actionSearch = request.getParameter("search");
        String categoryId = request.getParameter("categoryId");

        String mentoringPageRaw = request.getParameter("mentoringPage");
        int mentoringPage = parsePageNumber(mentoringPageRaw);
        PageControl mentoringPageControl = new PageControl();

        String studyingPageRaw = request.getParameter("studyingPage");
        int studyingPage = parsePageNumber(studyingPageRaw);
        PageControl studyingPageControl = new PageControl();

        List<Course> listCoursesMentoring = findCoursesForUser(userName, "mentoring", keyword, actionSearch, categoryId, mentoringPage, mentoringPageControl);
        List<Course> listCoursesStudying = findCoursesForUser(userName, "studying", keyword, actionSearch, categoryId, studyingPage, studyingPageControl);

        List<Category> listCategory = courseDAO.getAllCategories();

        session.setAttribute("listCoursesMentoring", listCoursesMentoring);
        session.setAttribute("listCoursesStudying", listCoursesStudying);
        session.setAttribute("listCategory", listCategory);
        session.setAttribute("mentoringPageControl", mentoringPageControl);
        session.setAttribute("studyingPageControl", studyingPageControl);

        request.getRequestDispatcher("myCourse.jsp").forward(request, response);
    }

    private List<Course> findCoursesForUser(String userName, String type, String keyword, String actionSearch, String categoryId, int page, PageControl pageControl) {
        List<Course> listCourses = new ArrayList<>();

        if ("mentoring".equals(type)) {
            if ("searchByName".equals(actionSearch)) {
                listCourses = courseDAO.searchMentoringCoursesByName(userName, keyword, page);
                pageControl.setTotalPage(courseDAO.countMentoringCoursesByName(userName, keyword));
            } else if ("category".equals(actionSearch) && categoryId != null) {
                listCourses = courseDAO.getMentoringCoursesByCategory(userName, categoryId, page);
                pageControl.setTotalPage(courseDAO.countMentoringCoursesByCategory(userName, categoryId));
            } else {
                listCourses = courseDAO.getMentoringCoursesByUser(userName, page);
                pageControl.setTotalPage(courseDAO.countMentoringCoursesByUser(userName));
            }
        } else if ("studying".equals(type)) {
            if ("searchByName".equals(actionSearch)) {
                listCourses = courseDAO.searchStudyingCoursesByName(userName, keyword, page);
                pageControl.setTotalPage(courseDAO.countStudyingCoursesByName(userName, keyword));
            } else if ("category".equals(actionSearch) && categoryId != null) {
                listCourses = courseDAO.getStudyingCoursesByCategory(userName, categoryId, page);
                pageControl.setTotalPage(courseDAO.countStudyingCoursesByCategory(userName, categoryId));
            } else {
                listCourses = courseDAO.getStudyingCoursesByUser(userName, page);
                pageControl.setTotalPage(courseDAO.countStudyingCoursesByUser(userName));
            }
        }

        pageControl.setPage(page);
        return listCourses;
    }

    private int parsePageNumber(String pageRaw) {
        try {
            return Integer.parseInt(pageRaw);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("viewMyCourses");
    }
}
