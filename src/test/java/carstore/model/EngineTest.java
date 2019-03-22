package carstore.model;

import carstore.model.car.Engine;
import org.junit.Test;

import static org.junit.Assert.*;


public class EngineTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Engine engine = new Engine();
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
        Engine main =
                new Engine().setEngineVolume(1200).setEngineType("gasoline");
        Engine copy =
                new Engine().setEngineVolume(1200).setEngineType("gasoline");
        Engine otherEngineVolume =
                new Engine().setEngineVolume(5000).setEngineType("gasoline");
        Engine otherEngineType =
                new Engine().setEngineVolume(1200).setEngineType("kerosene");
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
        Engine engine =
                new Engine().setEngineVolume(1200).setEngineType("gasoline");
        var result = engine.toString();
        var expected = String.format(
                "Engine{engineType='%s', engineVolume=%d}",
                engine.getEngineType(), engine.getEngineVolume());
        assertEquals(result, expected);
    }

}