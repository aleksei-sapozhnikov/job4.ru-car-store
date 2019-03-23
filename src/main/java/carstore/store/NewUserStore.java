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

    public User getByCredentials(Session ses, String login, String password) {
        return this.doTransactionWithRollback(ses, (session) -> {
            @SuppressWarnings("unchecked")
            var found = (List<User>) ses
                    .createQuery("from User where login=:login and password=:password")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .list();
            if (found.size() > 1) {
                throw new RuntimeException(String.format(
                        "Not unique user found by credentials: login = %spassword = %s",
                        login, password));
            }
            return found.isEmpty() ? null : found.get(0);
        });


    }
}
