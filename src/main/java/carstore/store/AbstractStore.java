package carstore.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.function.Function;

/**
 * General in-session store class.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class AbstractStore {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AbstractStore.class);

    private final SessionFactory factory;

    protected AbstractStore(SessionFactory factory) {
        this.factory = factory;
    }


    /**
     * Returns factory.
     *
     * @return Value of factory field.
     */
    protected SessionFactory getFactory() {
        return this.factory;
    }

    protected <T> T doTransaction(Function<Session, T> operations) {
        T result;
        try (var session = factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                result = operations.apply(session);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        return result;
    }
}
