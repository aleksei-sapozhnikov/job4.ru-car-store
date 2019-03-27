package carstore.listener;

import carstore.constants.ConstContext;
import carstore.logic.Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Context listener. Opens and closes resources.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebListener
public class ContextListener implements ServletContextListener {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(ContextListener.class);

    /**
     * Initializes objects used by servlets and puts them as attributes.
     *
     * @param sce ServletContextEvent object (to get ServletContext).
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var sessionFactory = new Configuration().configure().buildSessionFactory();
        var context = sce.getServletContext();
        context.setAttribute(ConstContext.SESSION_FACTORY.v(), sessionFactory);
        context.setAttribute(ConstContext.TRANSFORMER.v(), new Transformer());
    }

    /**
     * Closes open resources on application shutdown.
     *
     * @param sce ServletContextEvent object (to get ServletContext).
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        var context = sce.getServletContext();
        ((SessionFactory) context.getAttribute(ConstContext.SESSION_FACTORY.v())).close();
    }
}
