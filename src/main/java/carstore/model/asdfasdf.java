package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO: class description
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class asdfasdf {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(asdfasdf.class);

    private static Map<Car.IntParam, Integer> createDefaultIntParams() {
        var result = new HashMap<Car.IntParam, Integer>();
        result.put(Car.IntParam.PRICE, 1);
        result.put(Car.IntParam.YEAR_MANUFACTURED, 2);
        result.put(Car.IntParam.MILEAGE, 3);
        result.put(Car.IntParam.ENGINE_VOLUME, 4);
        return result;
    }

    private static Map<Car.StrParam, String> createDefaultStrParams() {
        var result = new HashMap<Car.StrParam, String>();
        result.put(Car.StrParam.MANUFACTURER, "manufacturer");
        result.put(Car.StrParam.MODEL, "model");
        result.put(Car.StrParam.NEWNESS, "newness");
        result.put(Car.StrParam.BODY_TYPE, "bodyType");
        result.put(Car.StrParam.COLOR, "color");
        result.put(Car.StrParam.ENGINE_FUEL, "engineFuel");
        result.put(Car.StrParam.TRANSMISSION_TYPE, "transmissionType");
        return result;
    }

    public static void main(String[] args) throws IOException {
        try (var hbFactory = new Configuration().configure().buildSessionFactory();
             var hbSession = hbFactory.openSession()) {
            var owner = User.of("login", "password", "phone");
            var car = Car.of(owner,
                    createDefaultStrParams(),
                    createDefaultIntParams());
            var images = Set.of(
                    Image.of(Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\123\\1E22.jpg"))),
                    Image.of(Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\123\\maxresdefault.jpg"))),
                    Image.of(Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\123\\640px-2016_Ford_Transit_350_2.2.jpg"))),
                    Image.of(Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\123\\used-2015-ford-focus-5drhatchbackse-9806-17346098-1-640.jpg"))),
                    Image.of(Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\123\\Без названия.jpg")))
            );
            images.forEach(image -> image.setCar(car));

            var tx = hbSession.beginTransaction();
            hbSession.persist(owner);
            hbSession.persist(car);
            images.forEach(hbSession::persist);
            tx.commit();
        }
    }
}
