package carstore.servlet;

import carstore.model.car.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

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


    public static void main(String[] args) throws IOException {
        try (var factory = new Configuration().configure().buildSessionFactory();
             var session = factory.openSession()) {
            var tx = session.beginTransaction();
            for (int i = 0; i < 10; i++) {
                Car car =
                        new Car().setPrice(1500)
                                .setAge(new Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
                                .setBody(new Body().setColor("black").setType("sedan"))
                                .setChassis(new Chassis().setTransmissionType("automatic"))
                                .setEngine(new Engine().setEngineType("gasoline").setEngineVolume(1200))
                                .setMark(new Mark().setManufacturer("Ford").setModel("FX-1200"))
                                .setImages(Arrays.asList(
                                        new Image().setData(Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\123\\2.jpg"))),
                                        new Image().setData(Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\123\\4.jpg")))
                                ));
                session.save(car);
            }
            tx.commit();
        }
    }
}
