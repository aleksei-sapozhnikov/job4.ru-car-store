package carstore.filter;

import carstore.constants.ConstContext;
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

    private SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        var context = filterConfig.getServletContext();
        this.sessionFactory = (SessionFactory) context.getAttribute(ConstContext.SESSION_FACTORY.v());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var session = (Session) request.getAttribute("hibernateSession");
        if (session == null) {
            session = this.sessionFactory.openSession();
            request.setAttribute("hibernateSession", session);
        }
        try {
            chain.doFilter(request, response);
            session.close();
        } catch (Exception e) {
            session.close();
            throw e;
        }
    }

    @Override
    public void destroy() {

    }
}
