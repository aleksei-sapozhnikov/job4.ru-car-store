package carstore.model;

import carstore.model.car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

/**
 * Item image entity.
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
    private long id;
    /**
     * Image data.
     */
    @Lob
    @Column(name = "image_data")
    private byte[] data;
    /**
     * Car this image is associated with.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

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
    public Image setId(long id) {
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

    /**
     * Returns car.
     *
     * @return Value of car field.
     */
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
