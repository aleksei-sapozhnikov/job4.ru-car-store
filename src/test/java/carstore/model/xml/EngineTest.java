package carstore.model.xml;

import org.junit.Test;

import static org.junit.Assert.*;


public class EngineTest {

    @Test
    public void testDefaultValuesGettersSetters() {
        Engine engine = new Engine();
        assertEquals(0, engine.getId());
        assertEquals(0, engine.getEngineVolume());
        assertNull(engine.getEngineType());
        //
        engine.setId(15);
        assertEquals(15, engine.getId());
        engine.setEngineVolume(1200);
        assertEquals(1200, engine.getEngineVolume());
        engine.setEngineType("gasoline");
        assertEquals("gasoline", engine.getEngineType());
    }

    @Test
    public void testEqualsHashcode() {
        Engine main =
                new Engine().setId(20).setEngineVolume(1200).setEngineType("gasoline");
        Engine copy =
                new Engine().setId(20).setEngineVolume(1200).setEngineType("gasoline");
        Engine otherId =
                new Engine().setId(99).setEngineVolume(1200).setEngineType("gasoline");
        Engine otherEngineVolume =
                new Engine().setId(20).setEngineVolume(5000).setEngineType("gasoline");
        Engine otherEngineType =
                new Engine().setId(20).setEngineVolume(1200).setEngineType("kerosene");
        // not equal
        assertNotEquals(main, null);
        assertNotEquals(main, "main");
        assertNotEquals(main, otherId);
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
                new Engine().setId(20).setEngineVolume(1200).setEngineType("gasoline");
        var result = engine.toString();
        var expected = String.format(
                "Engine{id=%d, engineType='%s', engineVolume=%d}",
                engine.getId(), engine.getEngineType(), engine.getEngineVolume());
        assertEquals(result, expected);
    }

}