package carstore.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AgeTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Age age = new Age();
        assertEquals(0, age.getId());
        assertEquals(0, age.getManufactureYear());
        assertEquals(0, age.getMileage());
        assertNull(age.getNewness());
        //
        age.setId(15);
        assertEquals(15, age.getId());
        age.setManufactureYear(2000);
        assertEquals(2000, age.getManufactureYear());
        age.setMileage(4000);
        assertEquals(4000, age.getMileage());
        age.setNewness("old");
        assertEquals("old", age.getNewness());
    }

    @Test
    public void testEqualsHashcode() {
        Age main =
                new Age().setId(20).setMileage(15_000).setManufactureYear(2003).setNewness("used");
        Age copy =
                new Age().setId(20).setMileage(15_000).setManufactureYear(2003).setNewness("used");
        Age otherId =
                new Age().setId(55).setMileage(15_000).setManufactureYear(2003).setNewness("used");
        Age otherMileage =
                new Age().setId(20).setMileage(99_999).setManufactureYear(2003).setNewness("used");
        Age otherManufactureYear =
                new Age().setId(20).setMileage(15_000).setManufactureYear(9999).setNewness("used");
        Age otherNewness =
                new Age().setId(20).setMileage(15_000).setManufactureYear(2003).setNewness("new");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherId);
        assertNotEquals(main, otherMileage);
        assertNotEquals(main, otherManufactureYear);
        assertNotEquals(main, otherNewness);
        // equal
        assertEquals(main, main);
        assertEquals(main.hashCode(), main.hashCode());
        assertEquals(main, copy);
        assertEquals(main.hashCode(), copy.hashCode());
    }

    @Test
    public void testToString() {
        Age age =
                new Age().setId(20).setMileage(15_000).setManufactureYear(2003).setNewness("used");
        var result = age.toString();
        var expected = String.format(
                "Age{id=%d, manufactureYear=%d, newness='%s', mileage=%d}",
                age.getId(), age.getManufactureYear(), age.getNewness(), age.getMileage());
        assertEquals(result, expected);
    }
}