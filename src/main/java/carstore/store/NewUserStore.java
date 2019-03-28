package carstore.store;

import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

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

    public NewUserStore(Session session) {
        super(session);
    }

    /**
     * Finds user by id
     *
     * @param id User id.
     * @return Found persistent user or <tt>null</tt> if not found.
     */
    public User get(long id) {
        return this.doTransaction(session -> session.get(User.class, id));
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
}
