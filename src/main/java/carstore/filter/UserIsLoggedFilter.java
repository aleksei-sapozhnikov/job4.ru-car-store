package carstore.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Checks if session contains logged user.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebFilter(filterName = "UserIsLoggedFilter")
public class UserIsLoggedFilter implements Filter {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(UserIsLoggedFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        var logged = req.getSession(false).getAttribute("loggedUser");
        if (logged != null) {
            chain.doFilter(request, response);
        } else {
            var msg = "Please log in to add or edit cars";
            resp.sendRedirect(String.format("%s/login?error=%s", req.getContextPath(), msg));
        }
    }

    @Override
    public void destroy() {

    }
}
