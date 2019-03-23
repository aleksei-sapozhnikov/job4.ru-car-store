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

    protected <T> T doTransactionWithCommit(SessionFactory factory, Function<Session, T> operations) {
        T result;
        try (var session = factory.openSession()) {
            result = this.doTransactionWithCommit(session, operations);
        }
        return result;
    }

    protected <T> T doTransactionWithCommit(Session session, Function<Session, T> operations) {
        T result;
        var tx = session.beginTransaction();
        try {
            result = operations.apply(session);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return result;
    }

    protected <T> T doTransactionWithRollback(SessionFactory factory, Function<Session, T> operations) {
        T result;
        try (var session = factory.openSession()) {
            result = this.doTransactionWithRollback(session, operations);
        }
        return result;
    }

    protected <T> T doTransactionWithRollback(Session session, Function<Session, T> operations) {
        T result;
        var tx = session.beginTransaction();
        try {
            result = operations.apply(session);
            tx.rollback();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return result;
    }
}
