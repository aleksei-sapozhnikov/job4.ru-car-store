package carstore.model;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class CarTest {

    private Map<Car.IntParam, Integer> createDefaultIntParams() {
        var result = new HashMap<Car.IntParam, Integer>();
        result.put(Car.IntParam.PRICE, 1);
        result.put(Car.IntParam.YEAR_MANUFACTURED, 2);
        result.put(Car.IntParam.MILEAGE, 3);
        result.put(Car.IntParam.ENGINE_VOLUME, 4);
        assertEquals(result.size(), Car.IntParam.values().length);
        return result;
    }

    private Map<Car.StrParam, String> createDefaultStrParams() {
        var result = new HashMap<Car.StrParam, String>();
        result.put(Car.StrParam.MANUFACTURER, "manufacturer");
        result.put(Car.StrParam.MODEL, "model");
        result.put(Car.StrParam.NEWNESS, "newness");
        result.put(Car.StrParam.BODY_TYPE, "bodyType");
        result.put(Car.StrParam.COLOR, "color");
        result.put(Car.StrParam.ENGINE_FUEL, "engineFuel");
        result.put(Car.StrParam.TRANSMISSION_TYPE, "transmissionType");
        assertEquals(result.size(), Car.StrParam.values().length);
        return result;
    }

    @Test
    public void whenCreatedUsingStaticOfMethodThenGetValuesRight() {
        var strParams = this.createDefaultStrParams();
        var intParams = this.createDefaultIntParams();
        var owner = Mockito.mock(User.class);
        var car = Car.of(owner, strParams, intParams);
        // initialized by default
        assertEquals(car.getId(), 0);
        assertTrue(car.isAvailable());
        assertEquals(car.getCreated(), 0);
        // object references
        assertSame(car.getOwner(), owner);
        // integer params
        assertEquals(car.getPrice(), (int) intParams.get(Car.IntParam.PRICE));
        assertEquals(car.getEngineVolume(), (int) intParams.get(Car.IntParam.ENGINE_VOLUME));
        assertEquals(car.getMileage(), (int) intParams.get(Car.IntParam.MILEAGE));
        assertEquals(car.getYearManufactured(), (int) intParams.get(Car.IntParam.YEAR_MANUFACTURED));
        // string params
        assertEquals(car.getManufacturer(), strParams.get(Car.StrParam.MANUFACTURER));
        assertEquals(car.getModel(), strParams.get(Car.StrParam.MODEL));
        assertEquals(car.getNewness(), strParams.get(Car.StrParam.NEWNESS));
        assertEquals(car.getBodyType(), strParams.get(Car.StrParam.BODY_TYPE));
        assertEquals(car.getColor(), strParams.get(Car.StrParam.COLOR));
        assertEquals(car.getEngineFuel(), strParams.get(Car.StrParam.ENGINE_FUEL));
        assertEquals(car.getTransmissionType(), strParams.get(Car.StrParam.TRANSMISSION_TYPE));
    }

    @Test
    public void whenValuesSetBySettersThenGetNewValues() {
        var strParams = this.createDefaultStrParams();
        var intParams = this.createDefaultIntParams();
        var owner = Mockito.mock(User.class);
        var car = Car.of(owner, strParams, intParams);
        // initialized by default
        car.setId(56);
        assertEquals(car.getId(), 56);
        car.setAvailable(false);
        assertFalse(car.isAvailable());
        car.setCreated(123L);
        assertEquals(car.getCreated(), 123L);
        // object references
        var newOwner = Mockito.mock(User.class);
        car.setOwner(newOwner);
        assertSame(car.getOwner(), newOwner);
        // integer params
        int count = 0;
        car.setPrice(100);
        assertEquals(100, car.getPrice());
        count++;
        car.setEngineVolume(200);
        assertEquals(200, car.getEngineVolume());
        count++;
        car.setMileage(300);
        assertEquals(300, car.getMileage());
        count++;
        car.setYearManufactured(400);
        assertEquals(400, car.getYearManufactured());
        count++;
        assertEquals(count, Car.IntParam.values().length);
        // string params
        count = 0;
        car.setManufacturer("new_manufacturer");
        assertEquals("new_manufacturer", car.getManufacturer());
        count++;
        car.setModel("new_model");
        assertEquals("new_model", car.getModel());
        count++;
        car.setNewness("new_newness");
        assertEquals("new_newness", car.getNewness());
        count++;
        car.setBodyType("new_bodyType");
        assertEquals("new_bodyType", car.getBodyType());
        count++;
        car.setColor("new_color");
        assertEquals("new_color", car.getColor());
        count++;
        car.setEngineFuel("new_engineFuel");
        assertEquals("new_engineFuel", car.getEngineFuel());
        count++;
        car.setTransmissionType("new_transmissionType");
        assertEquals("new_transmissionType", car.getTransmissionType());
        count++;
        assertEquals(count, Car.StrParam.values().length);
    }

}