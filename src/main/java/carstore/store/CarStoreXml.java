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
public class CarStoreXml extends AbstractCarStore {
    /**
     * Name of Hibernate config file for this factory.
     */
    public static final String HIBERNATE_CONFIG = "hibernate-xml.cfg.xml";
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CarStoreXml.class);
    /**
     * Singleton instance.
     */
    private static volatile CarStore instance;

    /**
     * Initializes needed fields.
     *
     * @param sessionFactory Hibernate session factory.
     */
    private CarStoreXml(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Singleton instance getter.
     *
     * @return Singleton instance.
     */
    public static CarStore getInstance() {
        if (CarStoreXml.instance == null) {
            synchronized (CarStoreXml.class) {
                if (CarStoreXml.instance == null) {
                    var factory = new Configuration().configure(HIBERNATE_CONFIG).buildSessionFactory();
                    CarStoreXml.instance = new CarStoreXml(factory);
                }
            }
        }
        return CarStoreXml.instance;
    }


}
