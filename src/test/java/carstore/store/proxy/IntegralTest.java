package carstore.store.proxy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

/**
 * Interface for integral tests.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public interface IntegralTest {
    SessionFactory REAL_HB_FACTORY = new Configuration().configure().buildSessionFactory();

    default void doIntegralTestWithRollback(Consumer<Session> operations) {
        try (var factory = RollbackProxy.create(REAL_HB_FACTORY);
             var session = factory.openSession()
        ) {
            operations.accept(session);
            session.clear();
        }
    }
}
