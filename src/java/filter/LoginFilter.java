package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginFilter implements Filter {

    private FilterConfig filterConfig = null;

    // Define a list of paths to exclude from filtering
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/login.jsp", // Login page
            "/register.jsp", // Registration page
            "/login", // No-access page
            "/register", // No-access page
            "/homeguest.jsp", // No-access page
            "/no-access.jsp", // No-access page
            "/viewBlogDetails.jsp", // No-access page
            "/listpost.jsp", // No-access page
            "/viewBlogDetail", // No-access page
            "/viewblogs", // No-access page
            "/viewcourse.jsp", // No-access page
            "/viewcourse", // No-access page
            "/viewCourseMentor", // No-access page
            "/allCourse", // No-access page
            "/CSS/*", // No-access page
            "/img/*", // No-access page
            "/blogimg/*", // No-access page
            "/footer.jsp", // No-access page
            "/header.jsp", // No-access page
            "/resetPass", // Any public resources
            "/home", // Any public resources
            "/forgetPass.jsp", // Any public resources
            "/verify.jsp", // Any public resources
            "/verify" // Any public resources
    );

    public LoginFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Check if the requested path is in the list of excluded paths
        if (isExcludedPath(path)) {
            chain.doFilter(request, response); // Skip the filter and continue
            return;
        }

        // Check if user session exists and if user attribute is present
        if (session == null || session.getAttribute("user") == null) {
            // If no user is found in the session, redirect to the no-access page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/no-access.jsp");
            return;
        }

        // If user is logged in, proceed with the filter chain
        chain.doFilter(request, response);
    }

    private boolean isExcludedPath(String path) {
        // Check if the path is directly in the exclusion list or matches a pattern (e.g., /public/*)
        for (String excludedPath : EXCLUDED_PATHS) {
            if (excludedPath.endsWith("/*")) {
                // Handle wildcard paths, e.g., /public/*
                String base = excludedPath.substring(0, excludedPath.length() - 2);
                if (path.startsWith(base)) {
                    return true;
                }
            } else if (excludedPath.equals(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    private void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
