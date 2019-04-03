package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Frontend item object. Similar object is on the front
 * side, so it's easy to pass values using JSON.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "unused"})
public class FrontItem {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(FrontItem.class);
    /**
     * Locale to format numbers.
     */
    private static final Locale LOCALE = new Locale("ru");
    /**
     * Car id in store.
     */
    private long carId;
    /**
     * Item title.
     */
    private String title;
    /**
     * Item price.
     */
    private String price;
    /**
     * Item seller (user).
     */
    private String seller;
    /**
     * Seller contacts.
     */
    private String contacts;
    /**
     * Is item available for sale or not.
     */
    private boolean available;
    /**
     * Map of item descriptions: title -> text
     */
    private Map<String, String> descriptions = new LinkedHashMap<>();
    /**
     * Map of item's image ids (to get them by id from server).
     */
    private Set<Long> imageIds = new LinkedHashSet<>();

    private FrontItem() {
    }

    /**
     * Returns item with formatted titles and descriptions.
     *
     * @param car    Car object.
     * @param images Car Image objects.
     * @return Frontend Item object to send as JSON.
     */
    public static FrontItem of(Car car, Set<Image> images) {
        var item = new FrontItem();
        item.carId = car.getId();
        item.title = car.getManufacturer() + " " + car.getModel();
        item.price = String.format(LOCALE, "%,d $", car.getPrice());
        item.seller = car.getOwner().getLogin();
        item.contacts = String.format("tel: %s", car.getOwner().getPhone());
        item.available = car.isAvailable();
        item.descriptions.put("Body", String.join("; ",
                car.getBodyType(),
                car.getColor()));
        item.descriptions.put("Age", String.join("; ",
                car.getNewness(),
                String.format("%d y.m.", car.getYearManufactured()),
                String.format(LOCALE, "%,d km", car.getMileage())));
        item.descriptions.put("Engine", String.join("; ",
                car.getEngineFuel(),
                String.format("%d cm³", car.getEngineVolume())));
        item.descriptions.put("Transmission", String.join("; ",
                car.getTransmissionType()));
        item.imageIds.addAll(images.stream().map(Image::getId).collect(Collectors.toList()));
        return item;
    }

    /**
     * Returns carId.
     *
     * @return Value of carId field.
     */
    public long getCarId() {
        return this.carId;
    }

    /**
     * Returns title.
     *
     * @return Value of title field.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns price.
     *
     * @return Value of price field.
     */
    public String getPrice() {
        return this.price;
    }

    /**
     * Returns seller.
     *
     * @return Value of seller field.
     */
    public String getSeller() {
        return this.seller;
    }

    /**
     * Returns contacts.
     *
     * @return Value of contacts field.
     */
    public String getContacts() {
        return this.contacts;
    }

    /**
     * Returns available.
     *
     * @return Value of available field.
     */
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * Returns descriptions.
     *
     * @return Value of descriptions field.
     */
    public Map<String, String> getDescriptions() {
        return new LinkedHashMap<>(this.descriptions);
    }

    /**
     * Returns imageIds.
     *
     * @return Value of imageIds field.
     */
    public Set<Long> getImageIds() {
        return new LinkedHashSet<>(this.imageIds);
    }
}
