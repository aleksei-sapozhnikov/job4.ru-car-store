package carstore.store;

import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

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

    /**
     * Finds user by his login and password.
     *
     * @param ses      Hibernate session object.
     * @param login    User login.
     * @param password User password.
     * @return Found persistent user or <tt>null</tt> if not found.
     */
    public User getByCredentials(Session ses, String login, String password) {
        return this.doTransactionWithRollback(ses, session -> {
            @SuppressWarnings("unchecked")
            var found = (List<User>) session
                    .createQuery("from User where login=:login and password=:password")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .list();
            this.checkLoginIsUnique(login, found.size());
            return found.isEmpty() ? null : found.get(0);
        });
    }

    /**
     * Checks whether there is user with such login stored or nor.
     *
     * @param ses   Hibernate session.
     * @param login Login to search for.
     * @return <tt>true</tt> if login found, <tt>false</tt> if not.
     */
    public boolean contains(Session ses, String login) {
        return this.doTransactionWithRollback(ses, session -> {
            var found = session.createQuery("from User where login = :login")
                    .setParameter("login", login)
                    .list();
            this.checkLoginIsUnique(login, found.size());
            return !(found.isEmpty());
        });
    }

    /**
     * Checks if result of finding by login is unique.
     *
     * @param login       Login searched for.
     * @param amountFound How many elements were found with this login.
     */
    private void checkLoginIsUnique(String login, int amountFound) {
        if (amountFound > 1) {
            throw new RuntimeException(String.format(
                    "Not unique login found: %s", login));
        }
    }

    /**
     * Creates user using given params.
     *
     * @param ses    Request object.
     * @param params User parameters map.
     * @return Persistent user object.
     */
    public User createAndStore(Session ses, Map<String, String> params) {
        return this.doTransactionWithCommit(ses, (session) -> {
            var user = new User();
            session.save(user);
            user.setLogin(params.get("login"));
            user.setPassword(params.get("password"));
            user.setPhone(params.get("phone"));
            return user;
        });
    }
}
