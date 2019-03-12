package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Car mark bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Mark {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Mark.class);

    /**
     * Unique id.
     */
    private int id;

    /**
     * Manufacturer (Ford, Mercedes)
     */
    private String manufacturer;
    /**
     * Model (FX-1800, AMG-380)
     */
    private String model;

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
