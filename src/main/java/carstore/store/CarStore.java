package carstore.store;

import carstore.model.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * Car storage.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class CarStore {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CarStore.class);

    private <T> Function<Session, T> doTransaction(Function<Session, T> operations) {
        return session -> {
            T result;
            var tx = session.beginTransaction();
            try {
                result = operations.apply(session);
                tx.commit();
            } catch (
                    Exception e) {
                tx.rollback();
                throw e;
            }
            return result;
        };
    }

    public Function<Session, Car> get(long id) {
        return this.doTransaction(
                session -> session.get(Car.class, id)
        );
    }
}
