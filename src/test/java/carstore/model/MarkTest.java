package carstore.model;

import carstore.model.car.Mark;
import org.junit.Test;

import static org.junit.Assert.*;


public class MarkTest {
    @Test
    public void testDefaultValuesGettersSetters() {
        Mark mark = new Mark();
        assertEquals(0, mark.getId());
        assertNull(mark.getManufacturer());
        assertNull(mark.getModel());
        //
        mark.setId(15);
        assertEquals(15, mark.getId());
        mark.setManufacturer("Toyota");
        assertEquals("Toyota", mark.getManufacturer());
        mark.setModel("Prius PX-12");
        assertEquals("Prius PX-12", mark.getModel());
    }

    @Test
    public void testEqualsHashcode() {
        Mark main =
                new Mark().setId(20).setManufacturer("Toyota").setModel("Prius FX-122");
        Mark copy =
                new Mark().setId(20).setManufacturer("Toyota").setModel("Prius FX-122");
        Mark otherId =
                new Mark().setId(984).setManufacturer("Toyota").setModel("Prius FX-122");
        Mark otherManufacturer =
                new Mark().setId(20).setManufacturer("Mercedes").setModel("Prius FX-122");
        Mark otherModel =
                new Mark().setId(20).setManufacturer("Toyota").setModel("Land Cruiser ML-12");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherId);
        assertNotEquals(main, otherManufacturer);
        assertNotEquals(main, otherModel);
        // equal
        assertEquals(main, main);
        assertEquals(main.hashCode(), main.hashCode());
        assertEquals(main, copy);
        assertEquals(main.hashCode(), copy.hashCode());
    }

    @Test
    public void testToString() {
        Mark mark =
                new Mark().setId(20).setManufacturer("Toyota").setModel("Land Cruiser ML-12");
        var result = mark.toString();
        var expected = String.format(
                "Mark{id=%d, manufacturer='%s', model='%s'}",
                mark.getId(), mark.getManufacturer(), mark.getModel());
        assertEquals(result, expected);
    }

}