package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * TODO: class description
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class asdf {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(asdf.class);

    public static void main(String[] args) {
        var user = User.of("login", "password", "phone");
        var intParams = new HashMap<Car.IntParam, Integer>();
        intParams.put(Car.IntParam.ENGINE_VOLUME, 1200);
        intParams.put(Car.IntParam.MILEAGE, 150_000);
        intParams.put(Car.IntParam.YEAR_MANUFACTURED, 1998);
        intParams.put(Car.IntParam.PRICE, 9999);
        var mainImage = Image.of(new byte[50]);
        var otherImages = new HashSet<>(Arrays.asList(Image.of(new byte[100]), Image.of(new byte[200])));
        var car = Car.of(user, new HashMap<>(), intParams, mainImage, otherImages);
        try (var factory = new Configuration().configure().buildSessionFactory();
             var session = factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                session.persist(car);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
