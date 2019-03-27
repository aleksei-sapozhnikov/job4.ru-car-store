package carstore.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

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

    private final Session session;

    protected AbstractStore(Session session) {
        this.session = session;
    }

    protected <T> T doTransaction(Function<Session, T> operations) {
        T result;
        var tx = this.session.beginTransaction();
        try {
            result = operations.apply(this.session);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return result;
    }
}
