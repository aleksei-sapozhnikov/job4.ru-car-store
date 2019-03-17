package carstore.model.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Objects;

/**
 * TCar body bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "body")
public class Body {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Body.class);

    /**
     * Unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_id")
    private long id;

    /**
     * Body type (sedan, track, SUV)
     */
    @Column(name = "body_type")
    private String type;
    /**
     * Body color (black, white, red)
     */
    @Column(name = "color")
    private String color;

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
        Body body = (Body) o;
        return id == body.id
                && Objects.equals(type, body.type)
                && Objects.equals(color, body.color);
    }

    /**
     * Returns this object's hashcode.
     *
     * @return Object's hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, type, color);
    }

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
    public Body setId(long id) {
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
