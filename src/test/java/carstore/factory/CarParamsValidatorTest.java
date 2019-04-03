package carstore.factory;

import carstore.model.Car;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class CarParamsValidatorTest {

    @Mock
    private Map<Car.StrParam, String> strParams;
    @Mock
    private Map<Car.IntParam, Integer> intParams;

    private CarParamsValidator validator = new CarParamsValidator();

    @Before
    public void initValidParams() {
        MockitoAnnotations.initMocks(this);
        when(this.strParams.get(Car.StrParam.MANUFACTURER)).thenReturn("Great Wall");
        when(this.strParams.get(Car.StrParam.MODEL)).thenReturn("RAV-4");
        when(this.strParams.get(Car.StrParam.COLOR)).thenReturn("blue");
        when(this.strParams.get(Car.StrParam.BODY_TYPE)).thenReturn("SUV");
        when(this.strParams.get(Car.StrParam.NEWNESS)).thenReturn("used");
        when(this.strParams.get(Car.StrParam.ENGINE_FUEL)).thenReturn("gasoline");
        when(this.strParams.get(Car.StrParam.TRANSMISSION_TYPE)).thenReturn("automatic");
        when(this.intParams.get(Car.IntParam.PRICE)).thenReturn(4000);
        when(this.intParams.get(Car.IntParam.YEAR_MANUFACTURED)).thenReturn(2000);
        when(this.intParams.get(Car.IntParam.MILEAGE)).thenReturn(150000);
        when(this.intParams.get(Car.IntParam.ENGINE_VOLUME)).thenReturn(1200);
    }

    @Test
    public void whenAllParametersValidThenTrue() {
        assertTrue(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidManufacturerThenFalse() {
        when(this.strParams.get(Car.StrParam.MANUFACTURER)).thenReturn("$Toyota#");
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidModelThenFalse() {
        when(this.strParams.get(Car.StrParam.MODEL)).thenReturn("_Rav_4");
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidColorThenFalse() {
        when(this.strParams.get(Car.StrParam.COLOR)).thenReturn("cyan3");
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidBodyTypeThenFalse() {
        when(this.strParams.get(Car.StrParam.BODY_TYPE)).thenReturn("^classic^");
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidNewnessThenFalse() {
        when(this.strParams.get(Car.StrParam.NEWNESS)).thenReturn("good");
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidEngineFuelThenFalse() {
        when(this.strParams.get(Car.StrParam.ENGINE_FUEL)).thenReturn("kerogas");
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidTransmissionTypeThenFalse() {
        when(this.strParams.get(Car.StrParam.TRANSMISSION_TYPE)).thenReturn("semiautomatic");
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidPriceThenFalse() {
        when(this.intParams.get(Car.IntParam.PRICE)).thenReturn(-67);
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidYearManufacturedThenFalse() {
        when(this.intParams.get(Car.IntParam.YEAR_MANUFACTURED)).thenReturn(1655);
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidMileageThenFalse() {
        when(this.intParams.get(Car.IntParam.MILEAGE)).thenReturn(-555);
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }

    @Test
    public void whenInvalidEngineVolumeThenFalse() {
        when(this.intParams.get(Car.IntParam.ENGINE_VOLUME)).thenReturn(0);
        assertFalse(this.validator.areValidParams(this.strParams, this.intParams));
    }
}