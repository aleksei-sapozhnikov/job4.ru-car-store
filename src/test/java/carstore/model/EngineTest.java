package carstore.model;

import carstore.model.car.Car;
import org.junit.Test;

import static org.junit.Assert.*;


public class EngineTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Car.Engine engine = new Car.Engine();
        assertEquals(0, engine.getEngineVolume());
        assertNull(engine.getEngineType());
        //
        engine.setEngineVolume(1200);
        assertEquals(1200, engine.getEngineVolume());
        engine.setEngineType("gasoline");
        assertEquals("gasoline", engine.getEngineType());
    }

    @Test
    public void testEqualsHashcode() {
        Car.Engine main =
                new Car.Engine().setEngineVolume(1200).setEngineType("gasoline");
        Car.Engine copy =
                new Car.Engine().setEngineVolume(1200).setEngineType("gasoline");
        Car.Engine otherEngineVolume =
                new Car.Engine().setEngineVolume(5000).setEngineType("gasoline");
        Car.Engine otherEngineType =
                new Car.Engine().setEngineVolume(1200).setEngineType("kerosene");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherEngineVolume);
        assertNotEquals(main, otherEngineType);
        // equal
        assertEquals(main, main);
        assertEquals(main.hashCode(), main.hashCode());
        assertEquals(main, copy);
        assertEquals(main.hashCode(), copy.hashCode());
    }

    @Test
    public void testToString() {
        Car.Engine engine =
                new Car.Engine().setEngineVolume(1200).setEngineType("gasoline");
        var result = engine.toString();
        var expected = String.format(
                "Engine{engineType='%s', engineVolume=%d}",
                engine.getEngineType(), engine.getEngineVolume());
        assertEquals(result, expected);
    }

}