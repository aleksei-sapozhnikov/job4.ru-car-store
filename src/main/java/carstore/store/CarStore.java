package carstore.store;

import carstore.model.Car;
import carstore.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
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
        return this.functionTransaction(
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
        return this.functionTransaction(
                session -> session.createQuery("from Car").list()
        );
    }

    public Consumer<Session> saveOrUpdate(Car car, Set<Image> carImages) {
        return this.consumerTransaction(session -> {
            session.saveOrUpdate(car);
            carImages.forEach(img -> {
                img.setCar(car);
                session.save(img);
            });
        });
    }

}