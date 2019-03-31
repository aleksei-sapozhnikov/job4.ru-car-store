package carstore.store;

import carstore.model.Car;
import carstore.model.Image;
import carstore.model.User;
import carstore.store.proxy.IntegralTest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ComplexStoreTest implements IntegralTest {

    private CarStore carStore = new CarStore();
    private ImageStore imageStore = new ImageStore();
    private UserStore userStore = new UserStore();

    private User user;
    private Set<Image> images;
    private Car car;

    @Before
    public void initObjects() {
        this.user = User.of("login", "password", "phone");
        this.images = Set.of(Image.of("img1".getBytes()), Image.of("img2".getBytes()), Image.of("img3".getBytes()));
        this.car = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
        // check stores are empty
        this.doIntegralTestWithRollback(session -> {
            assertEquals(0, this.carStore.getAll().apply(session).size());
            assertEquals(0, this.imageStore.getAll().apply(session).size());
            assertEquals(0, this.userStore.getAll().apply(session).size());
        });
    }

    @Test
    public void whenCarSavedThenCarAndImagesSaved() {
        this.doIntegralTestWithRollback((session) -> {
            // first - must exist user
            var saved = this.userStore.saveIfNotExists(user).apply(session);
            assertTrue(saved);
            // now saving car and images
            this.carStore.saveOrUpdate(car, images).accept(session);
            long id = this.car.getId();
            var found = this.carStore.get(id).apply(session);
            // check quantity of objects saved
            assertEquals(1, this.carStore.getAll().apply(session).size());
            assertEquals(3, this.imageStore.getAll().apply(session).size());
            assertEquals(1, this.userStore.getAll().apply(session).size());
            // car parameters
            assertEquals(1, this.carStore.getAll().apply(session).size());
            assertEquals(found.getId(), this.car.getId());
            assertEquals(found.getTransmissionType(), this.car.getTransmissionType());
            assertEquals(found.getEngineFuel(), this.car.getEngineFuel());
            assertEquals(found.getColor(), this.car.getColor());
            assertEquals(found.getEngineVolume(), this.car.getEngineVolume());
            assertEquals(found.getBodyType(), this.car.getBodyType());
            assertEquals(found.getManufacturer(), this.car.getManufacturer());
            assertEquals(found.getModel(), this.car.getModel());
            assertEquals(found.getMileage(), this.car.getMileage());
            assertEquals(found.getPrice(), this.car.getPrice());
            // car owner
            assertEquals(found.getOwner().getLogin(), this.user.getLogin());
            assertEquals(found.getOwner().getPassword(), this.user.getPassword());
            assertEquals(found.getOwner().getPhone(), this.user.getPhone());
        });
    }

    @Test
    public void whenCarUpdatedThenGetByIdParametersEqual() {
        this.doIntegralTestWithRollback((session) -> {
            // first - must exist user
            var saved = this.userStore.saveIfNotExists(user).apply(session);
            assertTrue(saved);
            // now saving car and images
            this.carStore.saveOrUpdate(car, images).accept(session);
            long savedId = this.car.getId();
            // check quantity of objects saved
            assertEquals(1, this.carStore.getAll().apply(session).size());
            assertEquals(3, this.imageStore.getAll().apply(session).size());
            assertEquals(1, this.userStore.getAll().apply(session).size());
            // making new car with saved id and update it
            session.detach(this.car);
            var newCar = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var newImages = Set.of(Image.of("img4".getBytes()), Image.of("img5".getBytes()));
            newCar.setId(savedId);
            newCar.setMileage(111111);
            newCar.setNewness("very very old");
            this.carStore.saveOrUpdate(newCar, newImages).accept(session);
            // check quantity of objects saved
            assertEquals(1, this.carStore.getAll().apply(session).size());
            assertEquals(2, this.imageStore.getAll().apply(session).size());
            assertEquals(1, this.userStore.getAll().apply(session).size());
            // new car parameters
            var found = this.carStore.get(savedId).apply(session);
            assertEquals(found.getMileage(), newCar.getMileage());
            assertEquals(found.getNewness(), newCar.getNewness());
            // old car parameters
            assertEquals(found.getEngineFuel(), this.car.getEngineFuel());
            assertEquals(found.getColor(), this.car.getColor());
            assertEquals(found.getEngineVolume(), this.car.getEngineVolume());
            assertEquals(found.getBodyType(), this.car.getBodyType());
            assertEquals(found.getManufacturer(), this.car.getManufacturer());
            assertEquals(found.getModel(), this.car.getModel());
            assertEquals(found.getPrice(), this.car.getPrice());
            // car owner
            assertEquals(found.getOwner().getLogin(), this.user.getLogin());
            assertEquals(found.getOwner().getPassword(), this.user.getPassword());
            assertEquals(found.getOwner().getPhone(), this.user.getPhone());
        });
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