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
import model.User;

public class AdminFilter implements Filter {

    private FilterConfig filterConfig = null;

    public AdminFilter() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRoleId() == 1) { // Assuming roleId 1 is for admin
                chain.doFilter(request, response);
                return;
            }
        }
        // User is not an admin or session is null, redirect to no-access page
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/no-access.jsp");
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {}
}
