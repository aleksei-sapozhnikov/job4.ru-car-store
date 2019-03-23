package carstore.store;

import carstore.model.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Car in-session storage.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class NewCarStore extends AbstractStore {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(NewCarStore.class);

    /**
     * Returns List of all cars stored.
     *
     * @param ses Current session.
     * @return List of cars stored.
     */
    @SuppressWarnings("unchecked")
    public List<Car> getAll(Session ses) {
        return this.doTransactionWithRollback(ses,
                session -> session.createQuery("from Car").list());
    }
}
