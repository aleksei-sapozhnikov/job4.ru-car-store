package carstore.model;

import carstore.model.car.Car;
import org.junit.Test;

import static org.junit.Assert.*;


public class ChassisTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Car.Chassis chassis = new Car.Chassis();
        assertNull(chassis.getTransmissionType());
        //
        chassis.setTransmissionType("automatic");
        assertEquals("automatic", chassis.getTransmissionType());
    }

    @Test
    public void testEqualsHashcode() {
        Car.Chassis main =
                new Car.Chassis().setTransmissionType("automatic");
        Car.Chassis copy =
                new Car.Chassis().setTransmissionType("automatic");
        Car.Chassis otherTransmissionType =
                new Car.Chassis().setTransmissionType("manual");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherTransmissionType);
        // equal
        assertEquals(main, main);
        assertEquals(main.hashCode(), main.hashCode());
        assertEquals(main, copy);
        assertEquals(main.hashCode(), copy.hashCode());
    }

    @Test
    public void testToString() {
        Car.Chassis chassis =
                new Car.Chassis().setTransmissionType("automatic");
        var result = chassis.toString();
        var expected = String.format(
                "Chassis{transmissionType='%s'}",
                chassis.getTransmissionType());
        assertEquals(result, expected);
    }

}