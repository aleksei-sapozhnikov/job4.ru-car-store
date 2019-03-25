package carstore.model.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Item age bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Embeddable
public class Age {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Age.class);
    /**
     * Year of manufacturing (2003).
     */
    @Column(name = "age_manufacture_year")
    private int manufactureYear;
    /**
     * Item newness (new, used).
     */
    @Column(name = "age_newness")
    private String newness;
    /**
     * Item mileage, kilometers (150 000)
     */
    @Column(name = "age_mileage")
    private long mileage;

    /**
     * Creates new Age object.
     *
     * @param manufactureYear Year of manufacturing.
     * @param newness         Newnewss state.
     * @param mileage         Mileage.
     * @return New Age object with given parameters.
     */
    public static Age of(int manufactureYear, String newness, long mileage) {
        return new Age()
                .setManufactureYear(manufactureYear)
                .setNewness(newness)
                .setMileage(mileage);
    }

    /* * * * * * * * * * * * * * * * * *
     * equals(), hashCode(), toString()
     * * * * * * * * * * * * * * * * * */

    /**
     * Object.equals() method override.
     *
     * @param o Other object.
     * @return <tt>true</tt> if this and given objects are equal, <tt>false</tt> if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Age age = (Age) o;
        return manufactureYear == age.manufactureYear
                && mileage == age.mileage
                && Objects.equals(newness, age.newness);
    }

    /**
     * Returns this object's hashcode.
     *
     * @return Object's hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(manufactureYear, newness, mileage);
    }

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Age{manufactureYear=%d, newness='%s', mileage=%d}",
                this.manufactureYear, this.newness, this.mileage);
    }

    /**
     * Returns manufactureYear.
     *
     * @return Value of manufactureYear field.
     */
    public int getManufactureYear() {
        return this.manufactureYear;
    }

    /* * * * * * * * * * * *
     * getters and setters
     * * * * * * * * * * * */

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
