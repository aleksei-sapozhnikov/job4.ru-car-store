package carstore.model.annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Objects;

/**
 * Car mark bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "mark")
public class Mark {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Mark.class);

    /**
     * Unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_id")
    private int id;

    /**
     * Manufacturer (Ford, Mercedes)
     */
    @Column(name = "manufacturer")
    private String manufacturer;
    /**
     * Model (FX-1800, AMG-380)
     */
    @Column(name = "model")
    private String model;

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
        Mark mark = (Mark) o;
        return id == mark.id &&
                Objects.equals(manufacturer, mark.manufacturer) &&
                Objects.equals(model, mark.model);
    }

    /**
     * Returns this object's hashcode.
     *
     * @return Object's hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, manufacturer, model);
    }

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Mark{id=%d, manufacturer='%s', model='%s'}",
                this.id, this.manufacturer, this.model);
    }

    /* * * * * * * * * * * *
     * getters and setters
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
    public Mark setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Returns manufacturer.
     *
     * @return Value of manufacturer field.
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * Sets manufacturer value.
     *
     * @param manufacturer Value to set.
     */
    public Mark setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    /**
     * Returns model.
     *
     * @return Value of model field.
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Sets model value.
     *
     * @param model Value to set.
     */
    public Mark setModel(String model) {
        this.model = model;
        return this;
    }
}
