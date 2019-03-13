package carstore.model;

import org.hibernate.cfg.Configuration;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Testing how entities are stored by Hibernate.
 */
public class StoreModelsTest {

    @Test
    public void whenSaveUsingXmlMappingThenAllStored() throws Exception {
        var age = new carstore.model.xml.Age().setNewness("new").setMileage(1500).setManufactureYear(2003);
        var body = new carstore.model.xml.Body().setColor("black").setType("sedan");
        var chassis = new carstore.model.xml.Chassis().setTransmissionType("automatic");
        var engine = new carstore.model.xml.Engine().setEngineType("gasoline").setEngineVolume(1200);
        var mark = new carstore.model.xml.Mark().setManufacturer("Ford").setModel("Transit 2080-MX");
        var car = new carstore.model.xml.Car()
                .setAge(age)
                .setBody(body)
                .setChassis(chassis)
                .setEngine(engine)
                .setMark(mark)
                .setPrice(5_000_000);
        // Using xml-based hibernate config
        try (var factory = new Configuration().configure("hibernate-xml.cfg.xml").buildSessionFactory()) {
            try (var session = factory.openSession()) {
                var transaction = session.beginTransaction();
                try {
                    session.save(age);
                    session.save(body);
                    session.save(chassis);
                    session.save(engine);
                    session.save(mark);
                    session.save(car);
                    transaction.commit();
                } catch (Exception e) {
                    transaction.rollback();
                    throw e;
                }
            }
            try (var session = factory.openSession()) {
                var transaction = session.beginTransaction();
                try {
                    var storedAge = session.get(carstore.model.xml.Age.class, age.getId());
                    var storedBody = session.get(carstore.model.xml.Body.class, body.getId());
                    var storedEngine = session.get(carstore.model.xml.Engine.class, engine.getId());
                    var storedMark = session.get(carstore.model.xml.Mark.class, mark.getId());
                    var storedCar = session.get(carstore.model.xml.Car.class, car.getId());
                    assertThat(storedAge, is(age));
                    assertThat(storedBody, is(body));
                    assertThat(storedEngine, is(engine));
                    assertThat(storedMark, is(mark));
                    assertThat(storedCar, is(car));
                    transaction.rollback();
                } catch (Exception e) {
                    transaction.rollback();
                    throw e;
                }
            }
        }
    }


    @Test
    public void whenSaveUsingAnnotationMappingThenAllStored() {
        var age = new carstore.model.annotations.Age().setNewness("new").setMileage(1500).setManufactureYear(2003);
        var body = new carstore.model.annotations.Body().setColor("black").setType("sedan");
        var chassis = new carstore.model.annotations.Chassis().setTransmissionType("automatic");
        var engine = new carstore.model.annotations.Engine().setEngineType("gasoline").setEngineVolume(1200);
        var mark = new carstore.model.annotations.Mark().setManufacturer("Ford").setModel("Transit 2080-MX");
        var car = new carstore.model.annotations.Car()
                .setAge(age)
                .setBody(body)
                .setChassis(chassis)
                .setEngine(engine)
                .setMark(mark)
                .setPrice(5_000_000);
        // Using default annotations-based hibernate config
        try (var factory = new Configuration().configure().buildSessionFactory()) {
            try (var session = factory.openSession()) {
                var transaction = session.beginTransaction();
                try {
                    session.save(age);
                    session.save(body);
                    session.save(chassis);
                    session.save(engine);
                    session.save(mark);
                    session.save(car);
                    transaction.commit();
                } catch (Exception e) {
                    transaction.rollback();
                    throw e;
                }
            }
            try (var session = factory.openSession()) {
                var transaction = session.beginTransaction();
                try {
                    var storedAge = session.get(carstore.model.annotations.Age.class, age.getId());
                    var storedBody = session.get(carstore.model.annotations.Body.class, body.getId());
                    var storedEngine = session.get(carstore.model.annotations.Engine.class, engine.getId());
                    var storedMark = session.get(carstore.model.annotations.Mark.class, mark.getId());
                    var storedCar = session.get(carstore.model.annotations.Car.class, car.getId());
                    assertThat(storedAge, is(age));
                    assertThat(storedBody, is(body));
                    assertThat(storedEngine, is(engine));
                    assertThat(storedMark, is(mark));
                    assertThat(storedCar, is(car));
                    transaction.rollback();
                } catch (Exception e) {
                    transaction.rollback();
                    throw e;
                }
            }
        }
    }


}
