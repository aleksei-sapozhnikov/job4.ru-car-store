package carstore.model;

import carstore.model.car.Car;
import org.junit.Test;

import static org.junit.Assert.*;


public class MarkTest {
    @Test
    public void testDefaultValuesGettersSetters() {
        Car.Mark mark = new Car.Mark();
        assertNull(mark.getManufacturer());
        assertNull(mark.getModel());
        //
        mark.setManufacturer("Toyota");
        assertEquals("Toyota", mark.getManufacturer());
        mark.setModel("Prius PX-12");
        assertEquals("Prius PX-12", mark.getModel());
    }

    @Test
    public void testEqualsHashcode() {
        Car.Mark main =
                new Car.Mark().setManufacturer("Toyota").setModel("Prius FX-122");
        Car.Mark copy =
                new Car.Mark().setManufacturer("Toyota").setModel("Prius FX-122");
        Car.Mark otherManufacturer =
                new Car.Mark().setManufacturer("Mercedes").setModel("Prius FX-122");
        Car.Mark otherModel =
                new Car.Mark().setManufacturer("Toyota").setModel("Land Cruiser ML-12");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
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
        Car.Mark mark =
                new Car.Mark().setManufacturer("Toyota").setModel("Land Cruiser ML-12");
        var result = mark.toString();
        var expected = String.format(
                "Mark{manufacturer='%s', model='%s'}",
                mark.getManufacturer(), mark.getModel());
        assertEquals(result, expected);
    }

}