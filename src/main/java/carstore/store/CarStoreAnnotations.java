package carstore.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class CarStoreAnnotations extends AbstractCarStore {
    /**
     * Name of Hibernate config file for this factory.
     */
    public static final String HIBERNATE_CONFIG = "hibernate.cfg.xml";
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CarStoreAnnotations.class);
    /**
     * Singleton instance.
     */
    private static volatile CarStore instance;

    /**
     * Initializes needed fields.
     *
     * @param sessionFactory Hibernate session factory.
     */
    private CarStoreAnnotations(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Singleton instance getter.
     *
     * @return Singleton instance.
     */
    public static CarStore getInstance() {
        if (CarStoreAnnotations.instance == null) {
            synchronized (CarStoreAnnotations.class) {
                if (CarStoreAnnotations.instance == null) {
                    var factory = new Configuration().configure(HIBERNATE_CONFIG).buildSessionFactory();
                    CarStoreAnnotations.instance = new CarStoreAnnotations(factory);
                }
            }
        }
        return CarStoreAnnotations.instance;
    }


}
