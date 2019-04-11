package carstore.store;

import org.hibernate.Session;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * General store interface capable to do transactions.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public interface Store {

    /**
     * Returns function doing one Hibernate transaction with result returned.
     *
     * @param operations Operations to perform.
     * @param <T>        Result type.
     * @return Function to which session can be applied.
     */
    default <T> Function<Session, T> functionTransaction(Function<Session, T> operations) {
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

    /**
     * Returns consumer doing one Hibernate transaction without returning result.
     *
     * @param operations Operations to perform.
     * @return Consumer to which session can be applied.
     */
    default Consumer<Session> consumerTransaction(Consumer<Session> operations) {
        return session -> {
            var tx = session.beginTransaction();
            try {
                operations.accept(session);
                tx.commit();
            } catch (
                    Exception e) {
                tx.rollback();
                throw e;
            }
        };
    }


}
