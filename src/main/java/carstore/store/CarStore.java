package carstore.store;

import carstore.model.Car;
import carstore.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.ZoneId;
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

    /**
     * Returns car by its id.
     *
     * @param id Car id.
     * @return Function to get car.
     */
    public Function<Session, Car> get(long id) {
        return this.functionTransaction(
                session -> session.get(Car.class, id)
        );
    }

    /**
     * Returns List of all cars stored.
     *
     * @return Function to get all cars.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, List<Car>> getAll() {
        return this.functionTransaction(
                session -> session.createQuery("from Car").list()
        );
    }

    /**
     * Saves or updates car with its images.
     * Save or update is defined by car's id.
     *
     * @param car       Car object to save/update.
     * @param carImages Set of car images.
     * @return Consumer to apply hibernate session to.
     */
    public Consumer<Session> saveOrUpdate(Car car, Set<Image> carImages) {
        return this.consumerTransaction(session -> {
            if (!carImages.isEmpty()) {
                session.createQuery("delete from Image where car.id = :carId")
                        .setParameter("carId", car.getId())
                        .executeUpdate();
            }
            session.saveOrUpdate(car);
            carImages.forEach(img -> {
                img.setCar(car);
                session.save(img);
            });
        });
    }

    /**
     * Gets all cars with at least one image.
     *
     * @return List of cars with images.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, List<Car>> getAllWithImage() {
        return this.functionTransaction(session ->
                session.createQuery("from Car c where c.id in (select distinct i.car.id from Image i)")
                        .list());
    }

    /**
     * Gets all cars created today.
     *
     * @return List of cars created today.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, List<Car>> getAllCreatedToday() {
        return this.functionTransaction(session ->
                session.createQuery("from Car c where c.created > :dayStart")
                        .setParameter("dayStart", LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .list());
    }

    /**
     * Gets all cars fitting given manufacturer template.
     *
     * @return List of cars fitting criteria.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, List<Car>> getByManufacturer(String manufacturerLike) {
        return this.functionTransaction(session ->
                session.createQuery("from Car c where c.manufacturer like :manufacturer")
                        .setParameter("manufacturer", String.format("%s%%", manufacturerLike))
                        .list());
    }
}