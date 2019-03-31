package carstore.model;

import com.sun.istack.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Item image entity.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "images")
public class Image {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Image.class);

    private long id;
    /**
     * Image data.
     */
    private byte[] data;
    /**
     * Car of this image.
     */
    private Car car;

    /**
     * Static creation method.
     *
     * @param data Image data.
     * @return New image object.
     */
    public static Image of(byte[] data, Car car) {
        var image = new Image();
        image.setData(data);
        image.setCar(car);
        return image;
    }

    /**
     * Static creation method.
     *
     * @param data Image data.
     * @return New image object.
     */
    public static Image of(byte[] data) {
        return of(data, null);
    }


    ////////////////////////////
    // equals() and hashCode()
    ////////////////////////////

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Image image = (Image) o;
//        return Arrays.equals(data, image.data);
//    }
//
//    @Override
//    public int hashCode() {
//        return Arrays.hashCode(data);
//    }

    //////////////////////////////////
    // standard getters and setters
    //////////////////////////////////


    /**
     * Returns id.
     *
     * @return Value of id field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return this.id;
    }

    /**
     * Sets id value.
     *
     * @param id Value to set.
     */
    public Image setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Returns data.
     *
     * @return Value of data field.
     */
    @Lob
    @NotNull
    @Column(name = "data", nullable = false)
    public byte[] getData() {
        return Arrays.copyOf(this.data, this.data.length);
    }

    /**
     * Sets data value.
     *
     * @param data Value to set.
     */
    public Image setData(byte[] data) {
        this.data = Arrays.copyOf(data, data.length);
        return this;
    }

    /**
     * Returns car.
     *
     * @return Value of car field.
     */
    @ManyToOne
    @JoinColumn(name = "car_id")
    public Car getCar() {
        return this.car;
    }

    /**
     * Sets car value.
     *
     * @param car Value to set.
     */
    public Image setCar(Car car) {
        this.car = car;
        return this;
    }
}
