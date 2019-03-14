package carstore.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class ChassisTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Chassis chassis = new Chassis();
        assertEquals(0, chassis.getId());
        assertNull(chassis.getTransmissionType());
        //
        chassis.setId(15);
        assertEquals(15, chassis.getId());
        chassis.setTransmissionType("automatic");
        assertEquals("automatic", chassis.getTransmissionType());
    }

    @Test
    public void testEqualsHashcode() {
        Chassis main =
                new Chassis().setId(20).setTransmissionType("automatic");
        Chassis copy =
                new Chassis().setId(20).setTransmissionType("automatic");
        Chassis otherId =
                new Chassis().setId(99).setTransmissionType("automatic");
        Chassis otherTransmissionType =
                new Chassis().setId(20).setTransmissionType("manual");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherId);
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
                new Chassis().setId(20).setTransmissionType("automatic");
        var result = chassis.toString();
        var expected = String.format(
                "Chassis{id=%d, transmissionType='%s'}",
                chassis.getId(), chassis.getTransmissionType());
        assertEquals(result, expected);
    }

}