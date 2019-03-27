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

    public NewCarStore(Session session) {
        super(session);
    }

    /**
     * Returns List of all cars stored.
     *
     * @return List of cars stored.
     */
    @SuppressWarnings("unchecked")
    public List<Car> getAll() {
        return this.doTransaction(session -> session.createQuery("from Car").list());
    }

    public Car get(long id) {
        return this.doTransaction(session -> session.get(Car.class, id));
    }

    public void save(Car car) {
        this.doTransaction(session -> {
            session.save(car);
            session.detach(car);
            return null;
        });
    }

    public void update(Car newCar) {
        this.doTransaction(session -> {
            session.update(newCar);
            return null;
        });
    }

    public boolean containsSeller(long sellerId) {
        return this.doTransaction(session -> {
            var found = session.createQuery("from Car where seller.id = :sellerId")
                    .setParameter("sellerId", sellerId)
                    .list();
            return found.size() > 0;
        });
    }
}
