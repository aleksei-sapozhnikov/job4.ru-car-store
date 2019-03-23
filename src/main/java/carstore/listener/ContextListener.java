package carstore.listener;

import carstore.constants.ServletContextAttributes;
import carstore.store.NewUserStore;
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

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var sessionFactory = new Configuration().configure().buildSessionFactory();
        var context = sce.getServletContext();
        context.setAttribute(ServletContextAttributes.SESSION_FACTORY.v(), sessionFactory);
        context.setAttribute(ServletContextAttributes.USER_STORE.v(), new NewUserStore());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        var context = sce.getServletContext();
        var sessionFactory = (SessionFactory)
                context.getAttribute(ServletContextAttributes.SESSION_FACTORY.v());
        sessionFactory.close();
    }
}
