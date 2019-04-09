package carstore.store.integral;

import carstore.model.Car;
import carstore.model.Image;
import carstore.model.User;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import carstore.store.UserStore;
import carstore.store.integral.proxy.IntegralTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
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


    @Test
    public void whenSelectCarsWithImagesThenListOfCarsWithAtLeastOneImage() {
        this.doIntegralTestWithRollback((session) -> {
            // user must exist before
            this.userStore.saveIfNotExists(user).apply(session);
            // create cars
            var img1 = Set.of(Image.of("img1".getBytes()), Image.of("img2".getBytes()), Image.of("img3".getBytes()));
            var img2 = Set.of(Image.of("img1".getBytes()));
            var carImg1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carImg2 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carNoImg1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carNoImg2 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            carImg1.setModel("Car with images one");
            carImg2.setModel("Car with images two");
            carNoImg1.setModel("Car without images one");
            carNoImg2.setModel("Car without images two");
            // operations
            this.carStore.saveOrUpdate(carImg1, img1).accept(session);
            this.carStore.saveOrUpdate(carNoImg1, Set.of()).accept(session);
            this.carStore.saveOrUpdate(carImg2, img2).accept(session);
            this.carStore.saveOrUpdate(carNoImg2, Set.of()).accept(session);
            var result = this.carStore.getAllWithImage().apply(session);
            // verify
            result.sort(Comparator.comparing(Car::getModel));
            assertEquals(result.size(), 2);
            assertEquals(result.get(0).getModel(), "Car with images one");
            assertEquals(result.get(1).getModel(), "Car with images two");
        });
    }

    @Test
    public void whenSelectCarsCreatedTodayThenReturnedCreatedToday() {
        this.doIntegralTestWithRollback((session) -> {
            // user must exist before
            this.userStore.saveIfNotExists(user).apply(session);
            // create cars
            var carToday1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carToday2 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carYesterday1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carLastWeek1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            carToday1.setModel("Car created today one");
            carToday2.setModel("Car created today two");
            carYesterday1.setModel("Car created yesterday one");
            carLastWeek1.setModel("Car created last week one");
            long todayStart = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            carToday1.setCreated(todayStart + 2);
            carToday2.setCreated(todayStart + 5943);
            carYesterday1.setCreated(todayStart - 5);
            carLastWeek1.setCreated(todayStart - 8 * 24 * 60 * 60 * 100);
            // operations
            this.carStore.saveOrUpdate(carToday1, Set.of()).accept(session);
            this.carStore.saveOrUpdate(carYesterday1, Set.of()).accept(session);
            this.carStore.saveOrUpdate(carLastWeek1, this.images).accept(session);
            this.carStore.saveOrUpdate(carToday2, this.images).accept(session);
            var result = this.carStore.getAllCreatedToday().apply(session);
            // verify
            result.sort(Comparator.comparing(Car::getModel));
            assertEquals(result.size(), 2);
            assertEquals(result.get(0).getModel(), "Car created today one");
            assertEquals(result.get(1).getModel(), "Car created today two");
        });
    }

    @Test
    public void whenSelectCarsByManufacturerThenReturnCarsWithGivenManufacturer() {
        this.doIntegralTestWithRollback((session) -> {
            // user must exist before
            this.userStore.saveIfNotExists(user).apply(session);
            // create cars
            var carFord1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carFord2 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carMercedes1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            var carToyota1 = Car.of(this.user, this.createDefaultStrParams(), this.createDefaultIntParams());
            carFord1.setModel("Car Ford one");
            carFord2.setModel("Car Ford two");
            carMercedes1.setModel("Car Mercedes one");
            carToyota1.setModel("Car Toyota one");
            carFord1.setManufacturer("Ford");
            carFord2.setManufacturer("Ford");
            carMercedes1.setManufacturer("Mercedes");
            carToyota1.setManufacturer("Toyota");
            // operations
            this.carStore.saveOrUpdate(carFord1, Set.of()).accept(session);
            this.carStore.saveOrUpdate(carToyota1, Set.of()).accept(session);
            this.carStore.saveOrUpdate(carFord2, this.images).accept(session);
            this.carStore.saveOrUpdate(carMercedes1, this.images).accept(session);
            var resultFord = this.carStore.getByManufacturer("Ford").apply(session);
            var resultCherry = this.carStore.getByManufacturer("Cherry").apply(session);
            // verify
            assertEquals(resultCherry.size(), 0);
            resultFord.sort(Comparator.comparing(Car::getModel));
            assertEquals(resultFord.size(), 2);
            assertEquals(resultFord.get(0).getModel(), "Car Ford one");
            assertEquals(resultFord.get(1).getModel(), "Car Ford two");
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