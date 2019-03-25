package carstore.logic;

import carstore.model.Image;
import carstore.model.Item;
import carstore.model.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Transforms object of one class into another one.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Transformer {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Transformer.class);

    /**
     * Transforms one Car object to one Item object.
     * Static method implementing logic.
     * No danger for this to be overridden.
     *
     * @param car Car object.
     * @return Item object.
     */
    private static Item staticCarToItem(Car car) {
        var item = new Item();
        item.setTitle(String.format("%s %s",
                car.getMark().getManufacturer(),
                car.getMark().getModel()));
        item.setStoreId(car.getId());
        item.setPrice(car.getPrice());
        item.setDescriptions(Transformer.carToDescriptionsForItem(car));
        item.setImagesIds(Transformer.imagesToIds(car.getImages()));
        return item;
    }

    /**
     * Converts Car attributes to Item's map of descriptions.
     *
     * @param car Cra object.
     * @return Map of descriptions for Item.
     */
    private static Map<String, String> carToDescriptionsForItem(Car car) {
        Map<String, String> descriptions = new LinkedHashMap<>();
        descriptions.put("Seller", String.join("; ",
                car.getSeller().getLogin(),
                car.getSeller().getPhone()));
        descriptions.put("Age", String.join("; ",
                car.getAge().getNewness(),
                String.valueOf(car.getAge().getManufactureYear()),
                String.valueOf(car.getAge().getMileage())));
        descriptions.put("Body", String.join("; ",
                car.getBody().getType(),
                car.getBody().getColor()));
        descriptions.put("Engine", String.join("; ",
                car.getEngine().getEngineType(),
                String.valueOf(car.getEngine().getEngineVolume())));
        descriptions.put("Chassis", String.join("; ",
                car.getChassis().getTransmissionType()));
        return descriptions;
    }

    /**
     * Transforms collection of cars to collection of Items.
     *
     * @param cars List of Car objects.
     * @return List of Item objects.
     */
    public Collection<Item> carToItem(Collection<Car> cars) {
        return cars.stream()
                .map(Transformer::staticCarToItem)
                .collect(Collectors.toList());
    }

    /**
     * Converts collection of images to collection of their id's.
     *
     * @param images Collection of images.
     * @return Collection of ids.
     */
    private static List<Long> imagesToIds(Collection<Image> images) {
        return images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }

    private <T> T doTransactionWithRollback(Session session, Function<Session, T> operations) {
        T result;
        var tx = session.beginTransaction();
        try {
            result = operations.apply(session);
            tx.rollback();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return result;
    }
}
