package carstore.listener;

import carstore.constants.Attributes;
import carstore.factory.CarFactory;
import carstore.factory.FrontItemFactory;
import carstore.factory.ImageFactory;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import carstore.store.UserStore;
import carstore.util.PropertiesHolder;
import com.google.gson.Gson;
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
        var ctx = sce.getServletContext();
        var contextPathGiven = ctx.getContextPath();
        var contextPathToUse = contextPathGiven.equals("") ? "/" : String.format("%s/", contextPathGiven);
        ctx.setAttribute(Attributes.ATR_CONTEXT_PATH.v(), contextPathToUse);
        ctx.setAttribute(Attributes.ATR_SELECT_VALUES.v(), new PropertiesHolder(ctx.getInitParameter("selectValuesResource")));
        ctx.setAttribute(Attributes.ATR_HB_FACTORY.v(), sessionFactory);
        ctx.setAttribute(Attributes.ATR_USER_STORE.v(), new UserStore());
        ctx.setAttribute(Attributes.ATR_IMAGE_STORE.v(), new ImageStore());
        ctx.setAttribute(Attributes.ATR_CAR_STORE.v(), new CarStore());
        ctx.setAttribute(Attributes.ATR_ITEM_FACTORY.v(), new FrontItemFactory());
        ctx.setAttribute(Attributes.ATR_JSON_PARSER.v(), new Gson());
        ctx.setAttribute(Attributes.ATR_CAR_FACTORY.v(), new CarFactory());
        ctx.setAttribute(Attributes.ATR_IMAGE_FACTORY.v(), new ImageFactory());
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
