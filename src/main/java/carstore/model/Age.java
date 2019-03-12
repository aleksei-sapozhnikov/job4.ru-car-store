package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Car age bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Age {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Age.class);

    /**
     * Unique id.
     */
    private int id;

    /**
     * Year of manufacturing (2003).
     */
    private int manufactureYear;
    /**
     * Car newness (new, used).
     */
    private String newness;
    /**
     * Car mileage, kilometers (150 000)
     */
    private long mileage;

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Age{id=%d, manufactureYear=%d, newness='%s', mileage=%d}",
                this.id, this.manufactureYear, this.newness, this.mileage);
    }

    /* * * * * * * * * * * *
     * GETTERS AND SETTERS
     * * * * * * * * * * * */

    /**
     * Returns id.
     *
     * @return Value of id field.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets id value.
     *
     * @param id Value to set.
     */
    public Age setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Returns manufactureYear.
     *
     * @return Value of manufactureYear field.
     */
    public int getManufactureYear() {
        return this.manufactureYear;
    }

    /**
     * Sets manufactureYear value.
     *
     * @param manufactureYear Value to set.
     */
    public Age setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
        return this;
    }

    /**
     * Returns newness.
     *
     * @return Value of newness field.
     */
    public String getNewness() {
        return this.newness;
    }

    /**
     * Sets newness value.
     *
     * @param newness Value to set.
     */
    public Age setNewness(String newness) {
        this.newness = newness;
        return this;
    }

    /**
     * Returns mileage.
     *
     * @return Value of mileage field.
     */
    public long getMileage() {
        return this.mileage;
    }

    /**
     * Sets mileage value.
     *
     * @param mileage Value to set.
     */
    public Age setMileage(long mileage) {
        this.mileage = mileage;
        return this;
    }
}
