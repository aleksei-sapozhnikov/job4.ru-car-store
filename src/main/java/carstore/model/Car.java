package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * Item bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "car")
public class Car {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Car.class);
    /**
     * Unique id.
     */
    private long id;
    /**
     * Map of string-type parameters.
     */
    private Map<StrParam, String> strParams = new HashMap<>();
    /**
     * Map of string-type parameters.
     */
    private Map<IntParam, Integer> intParams = new HashMap<>();
    /**
     * Car owner entity object.
     */
    private User owner;
    /**
     * Main image.
     */
    private Image mainImage;
    /**
     * Set of this car images.
     */
    private Set<Image> otherImages = new LinkedHashSet<>();

    /**
     * Creates Car object using map of given parameters.
     *
     * @param owner       Car owner entity object.
     * @param strParams   Map of car string-type parameters.
     * @param intParams   Map of car integer-type parameters.
     * @param mainImage   Car main image.
     * @param otherImages Set of car other images.
     * @return Car object.
     */
    public static Car of(User owner,
                         Map<StrParam, String> strParams,
                         Map<IntParam, Integer> intParams,
                         Image mainImage, Set<Image> otherImages) {
        var car = new Car();
        car.owner = owner;
        car.strParams = strParams;
        car.intParams = intParams;
        car.mainImage = mainImage;
        car.otherImages = otherImages;
        return car;
    }

    //////////////////////////////////
    // standard getters and setters
    //////////////////////////////////

    /**
     * Returns id.
     *
     * @return Value of id field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return this.id;
    }

    /**
     * Sets id value.
     *
     * @param id Value to set.
     */
    public Car setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Returns owner.
     *
     * @return Value of owner field.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner")
    public User getOwner() {
        return this.owner;
    }

    /**
     * Sets owner value.
     *
     * @param owner Value to set.
     */
    public Car setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Returns mainImage.
     *
     * @return Value of mainImage field.
     */
    @AttributeOverride(name = "data", column = @Column(name = "main_image", nullable = false))
    public Image getMainImage() {
        return this.mainImage;
    }

    /**
     * Sets mainImage value.
     *
     * @param mainImage Value to set.
     */
    public Car setMainImage(Image mainImage) {
        this.mainImage = mainImage;
        return this;
    }

    /**
     * Returns otherImages.
     *
     * @return Value of otherImages field.
     */
    @ElementCollection
    public Set<Image> getOtherImages() {
        return this.otherImages;
    }

    /**
     * Sets otherImages value.
     *
     * @param otherImages Value to set.
     */
    public Car setOtherImages(Set<Image> otherImages) {
        this.otherImages = otherImages;
        return this;
    }

    //////////////////////////////////////////////////////////////////
    // custom getters and setters for properties held in map objects
    //////////////////////////////////////////////////////////////////

    /**
     * Returns price.
     *
     * @return Value of price field.
     */
    @Column(name = "price", nullable = false)
    public int getPrice() {
        return this.intParams.get(IntParam.PRICE);
    }

    /**
     * Sets price value.
     *
     * @param price Value to set.
     */
    public Car setPrice(int price) {
        this.intParams.put(IntParam.PRICE, price);
        return this;
    }

    /**
     * Returns yearManufactured.
     *
     * @return Value of yearManufactured field.
     */
    @Column(name = "year_manufactured", nullable = false)
    public int getYearManufactured() {
        return this.intParams.get(IntParam.YEAR_MANUFACTURED);
    }

    /**
     * Sets yearManufactured value.
     *
     * @param yearManufactured Value to set.
     */
    public Car setYearManufactured(int yearManufactured) {
        this.intParams.put(IntParam.YEAR_MANUFACTURED, yearManufactured);
        return this;
    }

    /**
     * Returns mileage.
     *
     * @return Value of mileage field.
     */
    @Column(name = "mileage", nullable = false)
    public int getMileage() {
        return this.intParams.get(IntParam.MILEAGE);
    }

    /**
     * Sets mileage value.
     *
     * @param mileage Value to set.
     */
    public Car setMileage(int mileage) {
        this.intParams.put(IntParam.MILEAGE, mileage);
        return this;
    }

    /**
     * Returns mileage.
     *
     * @return Value of mileage field.
     */
    @Column(name = "engine_volume", nullable = false)
    public int getEngineVolume() {
        return this.intParams.get(IntParam.ENGINE_VOLUME);
    }

    /**
     * Sets engineVolume value.
     *
     * @param engineVolume Value to set.
     */
    public Car setEngineVolume(int engineVolume) {
        this.intParams.put(IntParam.ENGINE_VOLUME, engineVolume);
        return this;
    }

    ////////////////////////////
    // equals() and hashCode()
    ////////////////////////////

    ////////////////////////////
    // parameters enumerations
    ////////////////////////////

    /**
     * Enumerates parameters needed to create a car.
     */
    public enum StrParam {
        MANUFACTURER,
        MODEL,
        COLOR,
        BODY_TYPE,
        NEWNESS,
        ENGINE_FUEL,
        TRANSMISSION_TYPE;
    }

    /**
     * Enumerates parameters needed to create a car.
     */
    public enum IntParam {
        PRICE,
        YEAR_MANUFACTURED,
        MILEAGE,
        ENGINE_VOLUME;
    }

}
