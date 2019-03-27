//package carstore.model;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//
//public class CarTest {
//    @Test
//    public void testDefaultValuesGettersSetters() {
//        Car car = new Car();
//        assertEquals(0, car.getId());
//        assertEquals(0, car.getPrice());
//        assertNull(car.getAge());
//        assertNull(car.getBody());
//        assertNull(car.getChassis());
//        assertNull(car.getEngine());
//        assertNull(car.getMark());
//        //
//        car.setId(15);
//        assertEquals(15, car.getId());
//        car.setPrice(1500);
//        assertEquals(1500, car.getPrice());
//        car.setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000));
//        assertEquals(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000), car.getAge());
//        car.setBody(new Car.Body().setColor("black").setType("sedan"));
//        assertEquals(new Car.Body().setColor("black").setType("sedan"), car.getBody());
//        car.setChassis(new Car.Chassis().setTransmissionType("automatic"));
//        assertEquals(new Car.Chassis().setTransmissionType("automatic"), car.getChassis());
//        car.setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200));
//        assertEquals(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200), car.getEngine());
//        car.setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        assertEquals(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"), car.getMark());
//    }
//
//    @Test
//    public void testEqualsHashcode() {
//        Car main =
//                new Car().setId(20).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car copy =
//                new Car().setId(20).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car otherId =
//                new Car().setId(99).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car otherPrice =
//                new Car().setId(20).setPrice(9654)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car otherAge =
//                new Car().setId(20).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("new").setManufactureYear(1965).setMileage(434))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car otherBody =
//                new Car().setId(20).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("yellow").setType("pickup"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car otherChassis =
//                new Car().setId(20).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("manual"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car otherEngine =
//                new Car().setId(20).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("kerosene").setEngineVolume(5400))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        Car otherMark =
//                new Car().setId(20).setPrice(1500)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Mercedes").setModel("GLK-800"));
//        // check constructors
//        assertTrue(main.getId() == copy.getId()
//                && main.getPrice() == copy.getPrice()
//                && main.getAge().equals(copy.getAge())
//                && main.getBody().equals(copy.getBody())
//                && main.getChassis().equals(copy.getChassis())
//                && main.getEngine().equals(copy.getEngine())
//                && main.getMark().equals(copy.getMark()));
//        assertTrue(main.getId() != otherId.getId()
//                && main.getPrice() == otherId.getPrice()
//                && main.getAge().equals(otherId.getAge())
//                && main.getBody().equals(otherId.getBody())
//                && main.getChassis().equals(otherId.getChassis())
//                && main.getEngine().equals(otherId.getEngine())
//                && main.getMark().equals(otherId.getMark()));
//        assertTrue(main.getId() == otherPrice.getId()
//                && !(main.getPrice() == otherPrice.getPrice())
//                && main.getAge().equals(otherPrice.getAge())
//                && main.getBody().equals(otherPrice.getBody())
//                && main.getChassis().equals(otherPrice.getChassis())
//                && main.getEngine().equals(otherPrice.getEngine())
//                && main.getMark().equals(otherPrice.getMark()));
//        assertTrue(main.getId() == otherAge.getId()
//                && main.getPrice() == otherAge.getPrice()
//                && !(main.getAge().equals(otherAge.getAge()))
//                && main.getBody().equals(otherAge.getBody())
//                && main.getChassis().equals(otherAge.getChassis())
//                && main.getEngine().equals(otherAge.getEngine())
//                && main.getMark().equals(otherAge.getMark()));
//        assertTrue(main.getId() == otherBody.getId()
//                && main.getPrice() == otherBody.getPrice()
//                && main.getAge().equals(otherBody.getAge())
//                && !(main.getBody().equals(otherBody.getBody()))
//                && main.getChassis().equals(otherBody.getChassis())
//                && main.getEngine().equals(otherBody.getEngine())
//                && main.getMark().equals(otherBody.getMark()));
//        assertTrue(main.getId() == otherChassis.getId()
//                && main.getPrice() == otherChassis.getPrice()
//                && main.getAge().equals(otherChassis.getAge())
//                && main.getBody().equals(otherChassis.getBody())
//                && !(main.getChassis().equals(otherChassis.getChassis()))
//                && main.getEngine().equals(otherChassis.getEngine())
//                && main.getMark().equals(otherChassis.getMark()));
//        assertTrue(main.getId() == otherEngine.getId()
//                && main.getPrice() == otherEngine.getPrice()
//                && main.getAge().equals(otherEngine.getAge())
//                && main.getBody().equals(otherEngine.getBody())
//                && main.getChassis().equals(otherEngine.getChassis())
//                && !(main.getEngine().equals(otherEngine.getEngine()))
//                && main.getMark().equals(otherEngine.getMark()));
//        assertTrue(main.getId() == otherMark.getId()
//                && main.getPrice() == otherMark.getPrice()
//                && main.getAge().equals(otherMark.getAge())
//                && main.getBody().equals(otherMark.getBody())
//                && main.getChassis().equals(otherMark.getChassis())
//                && main.getEngine().equals(otherMark.getEngine())
//                && !(main.getMark().equals(otherMark.getMark())));
//        // not equal
//        assertNotEquals(main, null);
//        assertNotEquals(main, "main");
//        assertNotEquals(main, otherId);
//        assertNotEquals(main, otherPrice);
//        assertNotEquals(main, otherAge);
//        assertNotEquals(main, otherBody);
//        assertNotEquals(main, otherChassis);
//        assertNotEquals(main, otherEngine);
//        assertNotEquals(main, otherMark);
//        // equal
//        assertEquals(main, main);
//        assertEquals(main.hashCode(), main.hashCode());
//        assertEquals(main, copy);
//        assertEquals(main.hashCode(), copy.hashCode());
//    }
//
//    @Test
//    public void testToString() {
//        Car car =
//                new Car().setId(20)
//                        .setAge(new Car.Age().setNewness("used").setManufactureYear(2003).setMileage(15000))
//                        .setBody(new Car.Body().setColor("black").setType("sedan"))
//                        .setChassis(new Car.Chassis().setTransmissionType("automatic"))
//                        .setEngine(new Car.Engine().setEngineType("gasoline").setEngineVolume(1200))
//                        .setMark(new Car.Mark().setManufacturer("Ford").setModel("FX-1200"));
//        var result = car.toString();
//        var expected = String.format(
//                "Item{id=%d, price=%d, mark=%s, body=%s, age=%s, engine=%s, chassis=%s}",
//                car.getId(), car.getPrice(), car.getMark(), car.getBody(), car.getAge(), car.getEngine(), car.getChassis());
//        assertEquals(result, expected);
//    }
//
//}