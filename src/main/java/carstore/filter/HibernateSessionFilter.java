package carstore.filter;

import carstore.constants.Attributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Opens and closes Hibernate session for request.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebFilter(filterName = "HibernateSessionFilter")
public class HibernateSessionFilter implements Filter {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(HibernateSessionFilter.class);
    /**
     * Hibernate session factory.
     */
    private SessionFactory hbFactory;

    /**
     * Initializes fields.
     *
     * @param filterConfig Filter config object.
     */
    @Override
    public void init(FilterConfig filterConfig) {
        var ctx = filterConfig.getServletContext();
        this.hbFactory = (SessionFactory) ctx.getAttribute(Attributes.HB_FACTORY.v());
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
        var session = (Session) request.getAttribute(Attributes.HB_SESSION.v());
        if (session == null) {
            session = this.hbFactory.openSession();
            request.setAttribute(Attributes.HB_SESSION.v(), session);
        }
        try {
            chain.doFilter(request, response);
            session.close();
            request.removeAttribute(Attributes.HB_SESSION.v());
        } catch (Exception e) {
            session.clear();
            session.close();
            request.removeAttribute(Attributes.HB_SESSION.v());
            throw e;
        }
    }

    /**
     * Is called on filter destroy.
     */
    @Override
    public void destroy() {
    }
}
