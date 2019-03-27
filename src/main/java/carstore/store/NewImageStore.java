package carstore.store;

import carstore.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * Image in-session store.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class NewImageStore extends AbstractStore {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(NewImageStore.class);

    public NewImageStore(Session session) {
        super(session);
    }

    public Image get(long id) {
        return this.doTransaction(
                session -> session.get(Image.class, id));
    }
}
