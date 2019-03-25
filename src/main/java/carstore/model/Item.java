package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Item to send to frontend.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Item {
    public static Item of(int price, String title, Map<String, String> descriptions, Set<Image> images) {
        var item = new Item();
        item.setPrice(price);
        item.setTitle(title);
        item.setDescriptions(descriptions);
        item.setImagesIds(images.stream().map(Image::getId).collect(Collectors.toList()));
        return item;
    }

    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Item.class);
    /**
     * Item id in store.
     */
    private long storeId;
    /**
     * Price.
     */
    private int price;
    /**
     * Title.
     */
    private String title;
    /**
     * Map of descriptions.
     * Key: description title.
     * Value: description text.
     * <p>
     * E.g.: "Age" : "used; 320 000 km"
     */
    private Map<String, String> descriptions = new HashMap<>();
    /**
     * Id's of images for this item.
     */
    private List<Long> imagesIds;

    /* * * * * * * * * * * * * * * * * *
     * equals(), hashCode(), toString()
     * * * * * * * * * * * * * * * * * */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return storeId == item.storeId
                && price == item.price
                && Objects.equals(title, item.title)
                && Objects.equals(descriptions, item.descriptions)
                && Objects.equals(imagesIds, item.imagesIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, price, title, descriptions, imagesIds);
    }

    /* * * * * * * * * * * *
     * getters and setters
     * * * * * * * * * * * */

    /**
     * Returns storeId.
     *
     * @return Value of storeId field.
     */
    public long getStoreId() {
        return this.storeId;
    }

    /**
     * Sets storeId value.
     *
     * @param storeId Value to set.
     */
    public Item setStoreId(long storeId) {
        this.storeId = storeId;
        return this;
    }

    /**
     * Returns price.
     *
     * @return Value of price field.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Sets price value.
     *
     * @param price Value to set.
     */
    public Item setPrice(int price) {
        this.price = price;
        return this;
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
     * Sets title value.
     *
     * @param title Value to set.
     */
    public Item setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Returns descriptions.
     *
     * @return Value of descriptions field.
     */
    public Map<String, String> getDescriptions() {
        return this.descriptions;
    }

    /**
     * Sets descriptions value.
     *
     * @param descriptions Value to set.
     */
    public Item setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
        return this;
    }

    /**
     * Returns imagesIds.
     *
     * @return Value of imagesIds field.
     */
    public List<Long> getImagesIds() {
        return this.imagesIds;
    }

    /**
     * Sets imagesIds value.
     *
     * @param imagesIds Value to set.
     */
    public Item setImagesIds(List<Long> imagesIds) {
        this.imagesIds = imagesIds;
        return this;
    }
}
