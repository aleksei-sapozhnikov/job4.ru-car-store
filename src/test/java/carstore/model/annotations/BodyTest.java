package carstore.model.annotations;

import org.junit.Test;

import static org.junit.Assert.*;


public class BodyTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Body body = new Body();
        assertEquals(0, body.getId());
        assertNull(body.getColor());
        assertNull(body.getType());
        //
        body.setId(15);
        assertEquals(15, body.getId());
        body.setColor("black");
        assertEquals("black", body.getColor());
        body.setType("pickup");
        assertEquals("pickup", body.getType());
    }

    @Test
    public void testEqualsHashcode() {
        Body main =
                new Body().setId(20).setColor("yellow").setType("sedan");
        Body copy =
                new Body().setId(20).setColor("yellow").setType("sedan");
        Body otherId =
                new Body().setId(99).setColor("yellow").setType("sedan");
        Body otherColor =
                new Body().setId(20).setColor("red").setType("sedan");
        Body otherType =
                new Body().setId(20).setColor("yellow").setType("pickup");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherId);
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
                new Body().setId(20).setColor("yellow").setType("sedan");
        var result = body.toString();
        var expected = String.format(
                "Body{id=%d, type='%s', color='%s'}",
                body.getId(), body.getType(), body.getColor());
        assertEquals(result, expected);
    }

}