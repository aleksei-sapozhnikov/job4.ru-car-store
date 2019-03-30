package carstore.store;

import carstore.model.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;
import java.util.function.Function;

/**
 * Car storage.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class CarStore implements Store {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CarStore.class);

    public Function<Session, Car> get(long id) {
        return this.doTransaction(
                session -> session.get(Car.class, id)
        );
    }

    /**
     * Returns List of all cars stored.
     *
     * @return List of cars stored.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, List<Car>> getAll() {
        return this.doTransaction(
                session -> session.createQuery("from Car").list()
        );
    }
}
