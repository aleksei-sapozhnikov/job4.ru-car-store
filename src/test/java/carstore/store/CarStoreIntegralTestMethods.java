package carstore.store;

import carstore.model.*;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

/**
 * Test methods for CarStore integral tests.
 */
public class CarStoreIntegralTestMethods {

    static void whenMergeNotStoredIdThenSavedWithNewIds(CarStore store) {
        Car isAdd = new Car()
                .setPrice(5_000_000)
                .setAge(new Age().setNewness("new").setMileage(1500).setManufactureYear(2003))
                .setBody(new Body().setColor("black").setType("sedan"))
                .setChassis(new Chassis().setTransmissionType("automatic"))
                .setEngine(new Engine().setEngineType("gasoline").setEngineVolume(1200))
                .setMark(new Mark().setManufacturer("Ford").setModel("Transit 2080-MX"));
        var storedBefore = store.getAll().size();
        var saved = store.merge(isAdd);
        var storedAfter = store.getAll().size();
        isAdd.setId(saved.getId());
        isAdd.getAge().setId(saved.getAge().getId());
        isAdd.getBody().setId(saved.getBody().getId());
        isAdd.getChassis().setId(saved.getChassis().getId());
        isAdd.getEngine().setId(saved.getEngine().getId());
        isAdd.getMark().setId(saved.getMark().getId());
        //
        assertEquals(isAdd, saved);
        assertEquals(0, storedBefore);
        assertEquals(1, storedAfter);
    }

    static void whenMergeStoredIdThenUpdatedWithSameIds(CarStore store) {
        Car isAdd = new Car()
                .setPrice(5_000_000)
                .setAge(new Age().setNewness("new").setMileage(1500).setManufactureYear(2003))
                .setBody(new Body().setColor("black").setType("sedan"))
                .setChassis(new Chassis().setTransmissionType("automatic"))
                .setEngine(new Engine().setEngineType("gasoline").setEngineVolume(1200))
                .setMark(new Mark().setManufacturer("Ford").setModel("Transit 2080-MX"));
        Car isUpdate = new Car()
                .setPrice(10)
                .setAge(new Age().setNewness("update_new").setMileage(12).setManufactureYear(32))
                .setBody(new Body().setColor("update_black").setType("update_sedan"))
                .setChassis(new Chassis().setTransmissionType("update_automatic"))
                .setEngine(new Engine().setEngineType("update_gasoline").setEngineVolume(99))
                .setMark(new Mark().setManufacturer("update_Ford").setModel("update_Transit 2080-MX"));
        var storedBefore = store.getAll().size();
        var saved = store.merge(isAdd);
        isUpdate.setId(saved.getId());
        var updated = store.merge(isUpdate);
        var storedAfter = store.getAll().size();
        isUpdate.getAge().setId(updated.getAge().getId());
        isUpdate.getBody().setId(updated.getBody().getId());
        isUpdate.getChassis().setId(updated.getChassis().getId());
        isUpdate.getEngine().setId(updated.getEngine().getId());
        isUpdate.getMark().setId(updated.getMark().getId());
        //
        assertEquals(saved.getId(), updated.getId());
        assertEquals(isUpdate, updated);
        assertEquals(0, storedBefore);
        assertEquals(1, storedAfter);
    }

    static void whenGetThenSameAsSaved(CarStore store) {
        Car isAdd = new Car()
                .setPrice(5_000_000)
                .setAge(new Age().setNewness("new").setMileage(1500).setManufactureYear(2003))
                .setBody(new Body().setColor("black").setType("sedan"))
                .setChassis(new Chassis().setTransmissionType("automatic"))
                .setEngine(new Engine().setEngineType("gasoline").setEngineVolume(1200))
                .setMark(new Mark().setManufacturer("Ford").setModel("Transit 2080-MX"));
        var saved = store.merge(isAdd);
        int id = saved.getId();
        var got = store.get(new Car().setId(id));
        assertEquals(saved, got);
    }

    static void whenDeleteThenOutFromStorage(CarStore store) {
        Car isAdd = new Car()
                .setPrice(5_000_000)
                .setAge(new Age().setNewness("new").setMileage(1500).setManufactureYear(2003))
                .setBody(new Body().setColor("black").setType("sedan"))
                .setChassis(new Chassis().setTransmissionType("automatic"))
                .setEngine(new Engine().setEngineType("gasoline").setEngineVolume(1200))
                .setMark(new Mark().setManufacturer("Ford").setModel("Transit 2080-MX"));
        int id = store.merge(isAdd).getId();
        var storedBefore = store.getAll().size();
        store.delete(new Car().setId(id));
        var storedAfter = store.getAll().size();
        assertEquals(1, storedBefore);
        assertEquals(0, storedAfter);
    }

    static void whenGetAllThenListOfSavedEntities(CarStore store) {
        Car addOne = new Car()
                .setPrice(5_000_000)
                .setAge(new Age().setNewness("new").setMileage(1500).setManufactureYear(2003))
                .setBody(new Body().setColor("black").setType("sedan"))
                .setChassis(new Chassis().setTransmissionType("automatic"))
                .setEngine(new Engine().setEngineType("gasoline").setEngineVolume(1200))
                .setMark(new Mark().setManufacturer("Ford").setModel("Transit 2080-MX"));
        Car addTwo = new Car()
                .setPrice(10)
                .setAge(new Age().setNewness("update_new").setMileage(12).setManufactureYear(32))
                .setBody(new Body().setColor("update_black").setType("update_sedan"))
                .setChassis(new Chassis().setTransmissionType("update_automatic"))
                .setEngine(new Engine().setEngineType("update_gasoline").setEngineVolume(99))
                .setMark(new Mark().setManufacturer("update_Ford").setModel("update_Transit 2080-MX"));
        var savedOne = store.merge(addOne);
        var savedTwo = store.merge(addTwo);
        var allResult = store.getAll();
        var sizeAfter = store.getAll().size();
        var allExpected = Arrays.asList(savedOne, savedTwo);
        Comparator<Car> comparator = Comparator.comparingInt(Car::getId);
        allExpected.sort(comparator);
        allResult.sort(comparator);
        assertEquals(allExpected, allResult);
        assertEquals(2, sizeAfter);
    }

}