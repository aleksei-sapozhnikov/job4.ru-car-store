package carstore.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filter setting Response parameters.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebFilter(filterName = "ResponseParameterFilter")
public class ResponseParametersFilter implements Filter {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(ResponseParametersFilter.class);

    /**
     * Init filter method.
     * Unused.
     *
     * @param filterConfig Filter config object.
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Sets Character encoding to all response objects passing through.
     *
     * @param servletRequest  Request object.
     * @param servletResponse Response object.
     * @param filterChain     Filters on this resource.
     * @throws IOException      In case of filtering problems.
     * @throws ServletException In case of filtering problems.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Method on filter destroy.
     * Unused.
     */
    @Override
    public void destroy() {
    }
}
