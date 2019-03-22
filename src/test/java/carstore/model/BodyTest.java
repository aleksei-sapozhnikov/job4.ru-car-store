package carstore.model;

import carstore.model.car.Body;
import org.junit.Test;

import static org.junit.Assert.*;


public class BodyTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Body body = new Body();
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
        Body main =
                new Body().setColor("yellow").setType("sedan");
        Body copy =
                new Body().setColor("yellow").setType("sedan");
        Body otherColor =
                new Body().setColor("red").setType("sedan");
        Body otherType =
                new Body().setColor("yellow").setType("pickup");
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
        Body body =
                new Body().setColor("yellow").setType("sedan");
        var result = body.toString();
        var expected = String.format(
                "Body{type='%s', color='%s'}",
                body.getType(), body.getColor());
        assertEquals(result, expected);
    }

}