package carstore.store;

import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

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


}
