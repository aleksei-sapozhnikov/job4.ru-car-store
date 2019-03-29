package carstore.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class FrontItemTest {

    @Mock
    Car car;

    @Mock
    User user;

    @Mock
    Image image1, image2;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.initCarMock();
        this.initImagesMock();
    }

    private void initImagesMock() {
        when(this.image1.getId()).thenReturn(111L);
        when(this.image2.getId()).thenReturn(222L);
    }

    private void initCarMock() {
        when(car.getOwner()).thenReturn(this.user);
        when(user.getLogin()).thenReturn("login");
        when(user.getPhone()).thenReturn("phone");
        var count = 0;  // to check we gave all parameters stored in maps
        when(this.car.getManufacturer()).thenReturn("manufacturer");
        count++;
        when(this.car.getModel()).thenReturn("model");
        count++;
        when(this.car.getColor()).thenReturn("color");
        count++;
        when(this.car.getBodyType()).thenReturn("bodyType");
        count++;
        when(this.car.getNewness()).thenReturn("newness");
        count++;
        when(this.car.getEngineFuel()).thenReturn("engineFuel");
        count++;
        when(this.car.getTransmissionType()).thenReturn("transmissionType");
        count++;
        when(this.car.getPrice()).thenReturn(1);
        count++;
        when(this.car.getYearManufactured()).thenReturn(2);
        count++;
        when(this.car.getMileage()).thenReturn(3);
        count++;
        when(this.car.getEngineVolume()).thenReturn(4);
        count++;
        assertEquals(count, Car.StrParam.values().length + Car.IntParam.values().length);
    }

    @Test
    public void whenCreatedFromCarThenFieldsFormatted() {
        var images = Set.of(this.image1, this.image2);
        var item = FrontItem.of(this.car, images);
        assertEquals("manufacturer model", item.getTitle());
        assertEquals("1 $", item.getPrice());
        assertEquals("login", item.getSeller());
        assertEquals("tel: phone", item.getContacts());
        assertEquals(Map.of(
                "Body", "bodyType; color",
                "Age", "newness; 2 y.m.; 3 km",
                "Engine", "engineFuel; 4 cm³",
                "Transmission", "transmissionType"
                ),
                item.getDescriptions());
        assertEquals(Set.of(111L, 222L), item.getImageIds());
    }
}