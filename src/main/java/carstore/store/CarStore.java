package carstore.store;

import carstore.model.car.Car;

import java.util.List;

/**
 * Item store.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public interface CarStore extends AutoCloseable {
    /**
     * Save car to storage if given object's  id not found.
     * Updates existing car in storage if given object's id found.
     *
     * @param car Saved or updated object.
     */
    Car merge(Car car);

    /**
     * Returns object from storage with same id as given one.
     *
     * @param car Object with id needed to find.
     * @return Stored object with needed id or <tt>null</tt>.
     */
    Car get(Car car);

    /**
     * Deletes car from storage.
     *
     * @param car Item containing parameters defining object to delete.
     */
    void delete(Car car);

    /**
     * Returns list with all stored cars.
     *
     * @return List of all cars in storage.
     */
    @SuppressWarnings("unchecked")
    List<Car> getAll();
}
