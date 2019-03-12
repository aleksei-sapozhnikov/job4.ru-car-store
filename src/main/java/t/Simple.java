package t;

import carstore.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;

/**
 * Simple file to launch test.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Simple {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Simple.class);

    public int byTwo(int x) {
        return 2 * x;
    }

    public static void main(String[] args) {

        Age age = new Age().setNewness("new").setMileage(1500).setManufactureYear(2003);
        Body body = new Body().setColor("black").setType("sedan");
        Chassis chassis = new Chassis().setTransmissionType("automatic");
        Engine engine = new Engine().setEngineType("gasoline").setEngineVolume(1200);
        Mark mark = new Mark().setManufacturer("Ford").setModel("Transit 2080-MX");
        Car car = new Car()
                .setAge(age)
                .setBody(body)
                .setChassis(chassis)
                .setEngine(engine)
                .setMark(mark)
                .setPrice(5_000_000);


        try (var factory = new Configuration().configure().buildSessionFactory()) {
            try (var session = factory.openSession()) {
                var transaction = session.beginTransaction();
                session.save(age);
                session.save(body);
                session.save(chassis);
                session.save(engine);
                session.save(mark);

                System.out.println("Before save: " + car);
                session.save(car);
                System.out.println("After save: " + car);
                transaction.commit();
                System.out.println("After commit: " + car);
            }
        }
    }
}
