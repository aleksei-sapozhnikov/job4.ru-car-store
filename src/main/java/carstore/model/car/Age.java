package carstore.model.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Objects;

/**
 * Item age bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "age")
public class Age {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Age.class);

    /**
     * Unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "age_id")
    private long id;

    /**
     * Year of manufacturing (2003).
     */
    @Column(name = "manufacture_year")
    private int manufactureYear;
    /**
     * Item newness (new, used).
     */
    @Column(name = "newness")
    private String newness;
    /**
     * Item mileage, kilometers (150 000)
     */
    @Column(name = "mileage")
    private long mileage;

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
        return id == age.id
                && manufactureYear == age.manufactureYear
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
        return Objects.hash(id, manufactureYear, newness, mileage);
    }

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
     * getters and setters
     * * * * * * * * * * * */

    /**
     * Returns id.
     *
     * @return Value of id field.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets id value.
     *
     * @param id Value to set.
     */
    public Age setId(long id) {
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
