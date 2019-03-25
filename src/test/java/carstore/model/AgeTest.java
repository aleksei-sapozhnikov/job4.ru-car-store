package carstore.model;

import carstore.model.car.Car;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgeTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Car.Age age = new Car.Age();
        assertEquals(0, age.getManufactureYear());
        assertEquals(0, age.getMileage());
        assertNull(age.getNewness());
        //
        age.setManufactureYear(2000);
        assertEquals(2000, age.getManufactureYear());
        age.setMileage(4000);
        assertEquals(4000, age.getMileage());
        age.setNewness("old");
        assertEquals("old", age.getNewness());
    }

    @Test
    public void testEqualsHashcode() {
        Car.Age main =
                new Car.Age().setMileage(15_000).setManufactureYear(2003).setNewness("used");
        Car.Age copy =
                new Car.Age().setMileage(15_000).setManufactureYear(2003).setNewness("used");
        Car.Age otherMileage =
                new Car.Age().setMileage(99_999).setManufactureYear(2003).setNewness("used");
        Car.Age otherManufactureYear =
                new Car.Age().setMileage(15_000).setManufactureYear(9999).setNewness("used");
        Car.Age otherNewness =
                new Car.Age().setMileage(15_000).setManufactureYear(2003).setNewness("new");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
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
        Car.Age age =
                new Car.Age().setMileage(15_000).setManufactureYear(2003).setNewness("used");
        var result = age.toString();
        var expected = String.format(
                "Age{manufactureYear=%d, newness='%s', mileage=%d}",
                age.getManufactureYear(), age.getNewness(), age.getMileage());
        assertEquals(result, expected);
    }
}