package carstore.store;

import carstore.model.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

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

    public NewCarStore(SessionFactory factory) {
        super(factory);
    }

    /**
     * Returns List of all cars stored.
     *
     * @return List of cars stored.
     */
    @SuppressWarnings("unchecked")
    public List<Car> getAll() {
        return this.doTransaction(
                session -> session.createQuery("from Car").list());
    }

    public Car get(long id) {
        return this.doTransaction(session -> {
            var result = session.get(Car.class, id);
            session.detach(result);
            return result;
        });
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
            if (newCar.getImages().size() > 0) {
                var persistent = session.get(Car.class, newCar.getId());
                persistent.clearImages();
                session.detach(persistent);
                session.flush();
            }
            session.update(newCar);
            session.detach(newCar);
            return null;
        });
    }

    public void detach(Set<Car> cars) {
        this.doTransaction(session -> {
            cars.forEach(session::detach);
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
