package carstore.model;

import com.sun.istack.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Arrays;

/**
 * Item image entity.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Embeddable
public class Image {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Image.class);
    /**
     * Image data.
     */
    private byte[] data;

    /**
     * Static creation method.
     *
     * @param data Image data.
     * @return New image object.
     */
    public static Image of(byte[] data) {
        var image = new Image();
        image.data = data;
        return image;
    }

    ////////////////////////////
    // equals() and hashCode()
    ////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        return Arrays.equals(data, image.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    //////////////////////////////////
    // standard getters and setters
    //////////////////////////////////

    /**
     * Returns data.
     *
     * @return Value of data field.
     */
    @Lob
    @NotNull
    @Column(name = "image_data", nullable = false)
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
