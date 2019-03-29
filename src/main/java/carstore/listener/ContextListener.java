package carstore.listener;

import carstore.constants.Attributes;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import carstore.store.UserStore;
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
        context.setAttribute(Attributes.ATR_HB_FACTORY.v(), sessionFactory);
        context.setAttribute(Attributes.ATR_USER_STORE.v(), new UserStore());
        context.setAttribute(Attributes.ATR_IMAGE_STORE.v(), new ImageStore());
        context.setAttribute(Attributes.ATR_CAR_STORE.v(), new CarStore());
    }

    /**
     * Closes open resources on application shutdown.
     *
     * @param sce ServletContextEvent object (to get ServletContext).
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        var context = sce.getServletContext();
        ((SessionFactory) context.getAttribute(Attributes.ATR_HB_FACTORY.v())).close();
    }
}
