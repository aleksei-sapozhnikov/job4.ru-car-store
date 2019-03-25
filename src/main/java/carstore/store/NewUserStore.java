package carstore.store;

import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * User in-session store class.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class NewUserStore extends AbstractStore {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(NewUserStore.class);

    protected NewUserStore(SessionFactory factory) {
        super(factory);
    }

    /**
     * Finds user by his login and password.
     *
     * @param login    User login.
     * @param password User password.
     * @return Found persistent user or <tt>null</tt> if not found.
     */
    public User getByCredentials(String login, String password) {
        return this.doTransaction(session -> {
            @SuppressWarnings("unchecked")
            var found = (List<User>) session
                    .createQuery("from User where login = :login and password = :password")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .list();
            return found.isEmpty() ? null : found.get(0);
        });
    }

    /**
     * Returns whether user with given login exists in database.
     *
     * @param login Login to search for.
     * @return <tt>true</tt> if login found, <tt>false</tt> if not.
     */
    public boolean contains(String login) {
        return this.doTransaction(session -> {
            var found = session.createQuery("from User where login = :login")
                    .setParameter("login", login)
                    .list();
            return found.contains(login);
        });
    }

    /**
     * Saves user to store.
     *
     * @param user User to store.
     */
    public void save(User user) {
        this.doTransaction(session -> session.save(user));
    }
}
