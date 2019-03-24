package carstore.store;

import carstore.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import util.RollbackProxy;

import java.util.HashMap;
import java.util.function.Consumer;

import static org.junit.Assert.*;


public class NewUserStoreTest {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private final NewUserStore store = new NewUserStore();

    private User createUser(String login, String password, String phone) {
        var params = new HashMap<String, String>();
        params.put(User.Params.LOGIN.v(), login);
        params.put(User.Params.PASSWORD.v(), password);
        params.put(User.Params.PHONE.v(), phone);
        return User.from(params);
    }

    private void doIntegralTestWithRollback(Consumer<Session> operations) {
        try (var factory = RollbackProxy.create(this.factory);
             var session = factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                operations.accept(session);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Test
    public void whenSavedThenGetByCredentials() {
        this.doIntegralTestWithRollback(session -> {
            var login = "good_login";
            var password = "good_password";
            User saved = createUser(login, password, "some phone");
            this.store.save(saved).accept(session);
            var found = this.store.getByCredentials(login, password).apply(session);
            assertEquals(saved, found);
        });
    }

    @Test
    public void whenUserSavedThenContainsTrue() {
        this.doIntegralTestWithRollback(session -> {
            var login = "good login";
            User saved = createUser(login, "some password", "some phone");
            assertFalse(this.store.contains(login).apply(session));
            this.store.save(saved).accept(session);
            assertTrue(this.store.contains(login).apply(session));
        });
    }
}