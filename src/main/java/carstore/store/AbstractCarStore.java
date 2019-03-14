package carstore.store;

import carstore.model.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.function.Function;

/**
 * General Car store class.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public abstract class AbstractCarStore implements CarStore {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AbstractCarStore.class);

    /**
     * Hibernate session factory.
     */
    private final SessionFactory factory;

    /**
     * Initializes needed fields.
     *
     * @param sessionFactory Hibernate session factory.
     */
    protected AbstractCarStore(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    /**
     * Save car to storage if given object's  id not found.
     * Updates existing car in storage if given object's id found.
     *
     * @param car Saved or updated object.
     */
    @Override
    public Car merge(Car car) {
        return (Car) this.performTransaction(session ->
                session.merge(car)
        );
    }

    /**
     * Returns object from storage with same id as given one.
     *
     * @param car Object with id needed to find.
     * @return Stored object with needed id or <tt>null</tt>.
     */
    @Override
    public Car get(Car car) {
        return this.performTransaction(session ->
                session.get(Car.class, car.getId())
        );
    }

    /**
     * Deletes car from storage.
     *
     * @param car Car containing parameters defining object to delete.
     */
    @Override
    public void delete(Car car) {
        this.performTransaction(session -> {
            session.delete(car);
            return null;
        });
    }


    /**
     * Returns list with all stored cars.
     *
     * @return List of all cars in storage.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Car> getAll() {
        return this.performTransaction(session ->
                session.createQuery("from Car").list()
        );
    }

    /**
     * Performs transaction: creates session, performs given operations, commits and closes.
     *
     * @param function Function: operations to perform.
     * @param <T>      Result type.
     * @return Operation result.
     */
    private <T> T performTransaction(final Function<Session, T> function) {
        T result;
        try (final var session = this.factory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                result = function.apply(session);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
        return result;
    }

    /**
     * Clears table with entities.
     * For test purposes only.
     */
    void clear() {
        this.performTransaction(
                session -> session.createQuery("delete from Car").executeUpdate());
    }

    @Override
    public void close() {
        this.factory.close();
    }
}
