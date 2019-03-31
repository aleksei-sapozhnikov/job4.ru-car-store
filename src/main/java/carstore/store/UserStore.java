package carstore.store;

import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;
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
     * Finds user by his id.
     *
     * @param id User id.
     * @return Found persistent user or <tt>null</tt> if not found.
     */
    public Function<Session, User> get(long id) {
        return this.functionTransaction(
                session -> session.get(User.class, id)
        );
    }

    /**
     * Finds list of all users saved.
     *
     * @return Function to apply session to.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, List<User>> getAll() {
        return this.functionTransaction(
                session -> session.createQuery("from User").list()
        );
    }

    /**
     * Finds user by his login and password.
     *
     * @param login    User login.
     * @param password User password.
     * @return Found persistent user or <tt>null</tt> if not found.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, User> getByCredentials(String login, String password) {
        return this.functionTransaction(session -> {
            var found = (List<User>) session
                    .createQuery("from User where login = :login and password = :password")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .list();
            return found.isEmpty() ? null : found.get(0);
        });
    }

    /**
     * Saves user to store if user not exists.
     *
     * @param user User to store.
     * @return <tt>true</tt> if saved successfully, <tt>false</tt> if couldn't save (login exists).
     */
    public Function<Session, Boolean> saveIfNotExists(User user) {
        return this.functionTransaction(session -> {
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
