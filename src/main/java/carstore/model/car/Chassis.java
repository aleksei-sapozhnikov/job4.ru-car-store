package carstore.model.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Item chassis bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Embeddable
public class Chassis {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Chassis.class);

    /**
     * Transmission type (automatic, manual)
     */
    @Column(name = "chassis_transmission_type")
    private String transmissionType;

    /**
     * Creates new Chassis object.
     *
     * @param transmissionType Transmission type.
     * @return New Chassis object with given parameters.
     */
    public static Chassis of(String transmissionType) {
        return new Chassis()
                .setTransmissionType(transmissionType);
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
        Chassis chassis = (Chassis) o;
        return Objects.equals(transmissionType, chassis.transmissionType);
    }

    /**
     * Returns this object's hashcode.
     *
     * @return Object's hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(transmissionType);
    }

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Chassis{transmissionType='%s'}",
                this.transmissionType);
    }

    /* * * * * * * * * * * *
     * getters and setters
     * * * * * * * * * * * */

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
