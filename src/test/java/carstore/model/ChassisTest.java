package carstore.model;

import carstore.model.car.Chassis;
import org.junit.Test;

import static org.junit.Assert.*;


public class ChassisTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Chassis chassis = new Chassis();
        assertNull(chassis.getTransmissionType());
        //
        chassis.setTransmissionType("automatic");
        assertEquals("automatic", chassis.getTransmissionType());
    }

    @Test
    public void testEqualsHashcode() {
        Chassis main =
                new Chassis().setTransmissionType("automatic");
        Chassis copy =
                new Chassis().setTransmissionType("automatic");
        Chassis otherTransmissionType =
                new Chassis().setTransmissionType("manual");
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
        Chassis chassis =
                new Chassis().setTransmissionType("automatic");
        var result = chassis.toString();
        var expected = String.format(
                "Chassis{transmissionType='%s'}",
                chassis.getTransmissionType());
        assertEquals(result, expected);
    }

}