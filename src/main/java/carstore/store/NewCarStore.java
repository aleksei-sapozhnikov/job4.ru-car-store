package carstore.store;

import carstore.model.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

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
        return this.doTransaction(
                session -> session.get(Car.class, id));
    }

    public void save(Car car) {
        this.doTransaction(
                session -> session.save(car)
        );
    }

    public void update(Car newCar) {
        this.doTransaction(session -> {
            if (newCar.getImages().size() > 0) {
                var persistent = session.get(Car.class, newCar.getId());
                persistent.clearImages();
                session.flush();
                session.detach(persistent);
            }
            session.update(newCar);
            return null;
        });
    }
}
