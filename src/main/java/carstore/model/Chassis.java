package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Car chassis bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Chassis {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Chassis.class);

    /**
     * Unique id.
     */
    private int id;

    /**
     * Transmission type (automatic, manual)
     */
    private String transmissionType;

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
    public Chassis setId(int id) {
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
