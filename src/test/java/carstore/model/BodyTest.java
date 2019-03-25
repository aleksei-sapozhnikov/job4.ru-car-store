package carstore.model;

import carstore.model.car.Car;
import org.junit.Test;

import static org.junit.Assert.*;


public class BodyTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Car.Body body = new Car.Body();
        assertNull(body.getColor());
        assertNull(body.getType());
        //
        body.setColor("black");
        assertEquals("black", body.getColor());
        body.setType("pickup");
        assertEquals("pickup", body.getType());
    }

    @Test
    public void testEqualsHashcode() {
        Car.Body main =
                new Car.Body().setColor("yellow").setType("sedan");
        Car.Body copy =
                new Car.Body().setColor("yellow").setType("sedan");
        Car.Body otherColor =
                new Car.Body().setColor("red").setType("sedan");
        Car.Body otherType =
                new Car.Body().setColor("yellow").setType("pickup");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherColor);
        assertNotEquals(main, otherType);
        // equal
        assertEquals(main, main);
        assertEquals(main.hashCode(), main.hashCode());
        assertEquals(main, copy);
        assertEquals(main.hashCode(), copy.hashCode());
    }

    @Test
    public void testToString() {
        Car.Body body =
                new Car.Body().setColor("yellow").setType("sedan");
        var result = body.toString();
        var expected = String.format(
                "Body{type='%s', color='%s'}",
                body.getType(), body.getColor());
        assertEquals(result, expected);
    }

}