package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


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
     * Creates Car object using map of given parameters.
     *
     * @param owner     Car owner entity object.
     * @param strParams Map of car string-type parameters.
     * @param intParams Map of car integer-type parameters.
     * @return Car object.
     */
    public static Car of(User owner,
                         Map<StrParam, String> strParams,
                         Map<IntParam, Integer> intParams) {
        var car = new Car();
        car.owner = owner;
        car.strParams = strParams;
        car.intParams = intParams;
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
    @ManyToOne
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


    //////////////////////////////////////////////////////////////////
    // custom getters and setters for properties held in map objects
    //////////////////////////////////////////////////////////////////

    /* * * * * * * * * * *
     * integer parameters
     * * * * * * * * * * */

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
     * Returns engineVolume.
     *
     * @return Value of engineVolume field.
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

    /* * * * * * * * * * *
     * string parameters
     * * * * * * * * * * */

    /**
     * Returns manufacturer.
     *
     * @return Value of manufacturer field.
     */
    @Column(name = "manufacturer", nullable = false)
    public String getManufacturer() {
        return this.strParams.get(StrParam.MANUFACTURER);
    }

    /**
     * Sets manufacturer value.
     *
     * @param manufacturer Value to set.
     */
    public Car setManufacturer(String manufacturer) {
        this.strParams.put(StrParam.MANUFACTURER, manufacturer);
        return this;
    }

    /**
     * Returns model.
     *
     * @return Value of model field.
     */
    @Column(name = "model", nullable = false)
    public String getModel() {
        return this.strParams.get(StrParam.MODEL);
    }

    /**
     * Sets model value.
     *
     * @param model Value to set.
     */
    public Car setModel(String model) {
        this.strParams.put(StrParam.MODEL, model);
        return this;
    }

    /**
     * Returns color.
     *
     * @return Value of color field.
     */
    @Column(name = "color", nullable = false)
    public String getColor() {
        return this.strParams.get(StrParam.COLOR);
    }

    /**
     * Sets color value.
     *
     * @param color Value to set.
     */
    public Car setColor(String color) {
        this.strParams.put(StrParam.COLOR, color);
        return this;
    }

    /**
     * Returns bodyType.
     *
     * @return Value of bodyType field.
     */
    @Column(name = "body_type", nullable = false)
    public String getBodyType() {
        return this.strParams.get(StrParam.BODY_TYPE);
    }

    /**
     * Sets bodyType value.
     *
     * @param bodyType Value to set.
     */
    public Car setBodyType(String bodyType) {
        this.strParams.put(StrParam.BODY_TYPE, bodyType);
        return this;
    }

    /**
     * Returns newness.
     *
     * @return Value of newness field.
     */
    @Column(name = "newness", nullable = false)
    public String getNewness() {
        return this.strParams.get(StrParam.NEWNESS);
    }

    /**
     * Sets newness value.
     *
     * @param newness Value to set.
     */
    public Car setNewness(String newness) {
        this.strParams.put(StrParam.NEWNESS, newness);
        return this;
    }

    /**
     * Returns engineFuel.
     *
     * @return Value of engineFuel field.
     */
    @Column(name = "engine_fuel", nullable = false)
    public String getEngineFuel() {
        return this.strParams.get(StrParam.ENGINE_FUEL);
    }

    /**
     * Sets engineFuel value.
     *
     * @param engineFuel Value to set.
     */
    public Car setEngineFuel(String engineFuel) {
        this.strParams.put(StrParam.ENGINE_FUEL, engineFuel);
        return this;
    }

    /**
     * Returns transmissionType.
     *
     * @return Value of transmissionType field.
     */
    @Column(name = "transmission_type", nullable = false)
    public String getTransmissionType() {
        return this.strParams.get(StrParam.TRANSMISSION_TYPE);
    }

    /**
     * Sets transmissionType value.
     *
     * @param transmissionType Value to set.
     */
    public Car setTransmissionType(String transmissionType) {
        this.strParams.put(StrParam.TRANSMISSION_TYPE, transmissionType);
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
        TRANSMISSION_TYPE
    }

    /**
     * Enumerates parameters needed to create a car.
     */
    public enum IntParam {
        PRICE,
        YEAR_MANUFACTURED,
        MILEAGE,
        ENGINE_VOLUME
    }

}
