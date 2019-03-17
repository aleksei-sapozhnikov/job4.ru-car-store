package carstore.model.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Objects;

/**
 * Item chassis bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "chassis")
public class Chassis {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Chassis.class);

    /**
     * Unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chassis_id")
    private long id;

    /**
     * Transmission type (automatic, manual)
     */
    @Column(name = "transmission_type")
    private String transmissionType;

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
        Chassis chassis = (Chassis) o;
        return id == chassis.id
                && Objects.equals(transmissionType, chassis.transmissionType);
    }

    /**
     * Returns this object's hashcode.
     *
     * @return Object's hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, transmissionType);
    }

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Chassis{id=%d, transmissionType='%s'}",
                this.id, this.transmissionType);
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
    public Chassis setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Returns transmissionType.
     *
     * @return Value of transmissionType field.
     */
    public String getTransmissionType() {
        return this.transmissionType;
    }

    /**
     * Sets transmissionType value.
     *
     * @param transmissionType Value to set.
     */
    public Chassis setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
        return this;
    }
}
