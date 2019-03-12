package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TCar body bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Body {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Body.class);

    /**
     * Unique id.
     */
    private int id;

    /**
     * Body type (sedan, track, SUV)
     */
    private String type;
    /**
     * Body color (black, white, red)
     */
    private String color;

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Body{id=%d, type='%s', color='%s'}",
                this.id, this.type, this.color);
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
    public Body setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Returns type.
     *
     * @return Value of type field.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets type value.
     *
     * @param type Value to set.
     */
    public Body setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Returns color.
     *
     * @return Value of color field.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Sets color value.
     *
     * @param color Value to set.
     */
    public Body setColor(String color) {
        this.color = color;
        return this;
    }
}
