package carstore.factory;

import carstore.constants.Attributes;
import carstore.model.Car;
import carstore.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({Car.class, Utils.class})
public class CarFactoryTest {

    @Mock
    HttpServletRequest req;
    @Mock
    User user;
    @Mock
    Part idPart, otherPart;
    @Mock
    Car car;

    @Before
    public void initMocks() throws IOException, ServletException {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Utils.class);
        when(Utils.readLong(this.idPart)).thenReturn(111L);
        var strParams = this.createDefaultStrParams();
        when(Utils.readString(this.otherPart))
                .thenReturn(strParams.get(Car.StrParam.MANUFACTURER))
                .thenReturn(strParams.get(Car.StrParam.MODEL))
                .thenReturn(strParams.get(Car.StrParam.NEWNESS))
                .thenReturn(strParams.get(Car.StrParam.BODY_TYPE))
                .thenReturn(strParams.get(Car.StrParam.COLOR))
                .thenReturn(strParams.get(Car.StrParam.ENGINE_FUEL))
                .thenReturn(strParams.get(Car.StrParam.TRANSMISSION_TYPE))
                .thenThrow(new RuntimeException("Return values ended"));
        var intParams = this.createDefaultIntParams();
        when(Utils.readInteger(this.otherPart))
                .thenReturn(intParams.get(Car.IntParam.PRICE))
                .thenReturn(intParams.get(Car.IntParam.YEAR_MANUFACTURED))
                .thenReturn(intParams.get(Car.IntParam.MILEAGE))
                .thenReturn(intParams.get(Car.IntParam.ENGINE_VOLUME))
                .thenThrow(new RuntimeException("Return values ended"));
        /*
         These are default values that are taken from request
         in normal case. They will be changed in exception-checking tests.
         */
        // first 'any string' then 'particular case'
        when(this.req.getPart(any(String.class))).thenReturn(this.otherPart);
        when(this.req.getPart(Attributes.PRM_CAR_ID.v())).thenReturn(this.idPart);
    }

    @Test
    public void whenAllParametersPresentThenCreateCar() throws IOException, ServletException {
        PowerMockito.mockStatic(Car.class);
        when(Car.of(this.user, this.createDefaultStrParams(), createDefaultIntParams())).thenReturn(this.car);
        // actions
        var factory = new CarFactory();
        var result = factory.createCar(this.req, this.user);
        // verify
        verify(this.car).setId(111);
        assertSame(result, this.car);
    }

    @Test
    public void whenNotAllStringPartsPresentThenServletException() throws IOException, ServletException {
        /*
         string params are read first, so we place some of
         the first parts 'null' and must get exception
         */
        // first 'any string' then 'particular case'
        when(this.req.getPart(any(String.class))).thenReturn(
                this.otherPart, null, this.otherPart, this.otherPart,
                null, this.otherPart, null);
        when(this.req.getPart(Attributes.PRM_CAR_ID.v())).thenReturn(this.idPart);
        // actions
        var factory = new CarFactory();
        var wasException = new boolean[]{false};
        try {
            factory.createCar(this.req, this.user);
        } catch (ServletException e) {
            wasException[0] = true;
            assertEquals("Not all car String parameters found.", e.getMessage());
        }
        assertTrue(wasException[0]);
    }

    @Test
    public void whenNotAllIntegerPartsPresentThenServletException() throws IOException, ServletException {
        /*
         integer parameters are read after string, so we give enough
         string not-null parts and then some 'null' of integer parts.
         */
        // first 'any string' then 'particular case'
        when(this.req.getPart(any(String.class))).thenReturn(
                this.otherPart, this.otherPart, this.otherPart, this.otherPart,
                this.otherPart, this.otherPart, this.otherPart, this.otherPart,
                null, this.otherPart, null, this.otherPart, this.otherPart, null);
        when(this.req.getPart(Attributes.PRM_CAR_ID.v())).thenReturn(this.idPart);
        // actions
        var factory = new CarFactory();
        var wasException = new boolean[]{false};
        try {
            factory.createCar(this.req, this.user);
        } catch (ServletException e) {
            wasException[0] = true;
            assertEquals("Not all car Integer parameters found.", e.getMessage());
        }
        assertTrue(wasException[0]);
    }

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
}