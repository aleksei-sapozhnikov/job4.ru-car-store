package carstore.filter;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
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

    /**
     * Initializes on filter creation.
     *
     * @param filterConfig Filter config object.
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Creates Hibernate session and saves it as request parameter.
     * When request object comes back from servlets, closes the session.
     *
     * @param request  Request object
     * @param response Response object.
     * @param chain    Filter chein.
     * @throws IOException      In case of problems working with servlets.
     * @throws ServletException In case of problems working with servlets.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        var loggedId = req.getSession(false).getAttribute(Attributes.LOGGED_USER_ID.v());
        if (loggedId != null) {
            chain.doFilter(request, response);
        } else {
            var redirectPath = new StringBuilder()
                    .append(WebApp.BASEDIR.v()).append("/").append(WebApp.SRV_LOGIN.v())
                    .append("?")
                    .append(WebApp.ERROR_MSG).append("Please log in to add or edit cars")
                    .toString();
            resp.sendRedirect(redirectPath);
        }
    }

    /**
     * Is called on filter destroy.
     */
    @Override
    public void destroy() {

    }
}
