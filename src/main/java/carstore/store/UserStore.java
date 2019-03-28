package carstore.store;

import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * User store. Returns functions to which
 * current Hibernate session can be applied.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class UserStore implements Store {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(UserStore.class);

    /**
     * Saves user to store if user not exists.
     *
     * @param user User to store.
     * @return <tt>true</tt> if saved successfully, <tt>false</tt> if couldn't save (login exists).
     */
    public Function<Session, Boolean> saveIfNotExists(User user) {
        return Store.doTransaction(session -> {
            var found = session.createQuery("from User where login = :login")
                    .setParameter("login", user.getLogin())
                    .list();
            var isSaved = false;
            if (found.size() == 0) {
                session.persist(user);
                isSaved = true;
            }
            return isSaved;
        });
    }
}
