package carstore.store;

import carstore.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;
import java.util.function.Function;

/**
 * Image store.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class ImageStore implements Store {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(ImageStore.class);

    /**
     * Returns image by id.
     *
     * @param id Image entity id.
     * @return Image object or <tt>null</tt> if object not found in storage.
     */
    public Function<Session, Image> get(long id) {
        return Store.doTransaction(
                session -> session.get(Image.class, id)
        );
    }

    /**
     * Returns image for car with given id.
     *
     * @param carId Car id.
     * @return Image object or <tt>null</tt> if object not found in storage.
     */
    @SuppressWarnings("unchecked")
    public Function<Session, List<Image>> getForCar(long carId) {
        return Store.doTransaction(session ->
                session.createQuery("from Image where car.id = :carId")
                        .setParameter("carId", carId)
                        .list()
        );
    }
}
