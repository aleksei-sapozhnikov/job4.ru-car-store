package carstore.experiments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

/**
 * Car image entity.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "image")
public class Image {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Image.class);
    /**
     * Image id.
     */
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Image data.
     */
    @Lob
    @Column(name = "image_data")
    private byte[] data;

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
    public Image setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Returns data.
     *
     * @return Value of data field.
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Sets data value.
     *
     * @param data Value to set.
     */
    public Image setData(byte[] data) {
        this.data = data;
        return this;
    }
}
