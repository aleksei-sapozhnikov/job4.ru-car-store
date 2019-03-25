package carstore.model.car;

import carstore.model.Image;
import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static carstore.model.car.Car.Params.*;

/**
 * Item bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "car")
public class Car {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Car.class);
    /**
     * Unique id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private long id;
    /**
     * Price
     */
    @Column(name = "car_price")
    private int price;
    /**
     * User who is selling the car.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_seller")
    private User seller;
    /**
     * Set of this car images.
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Image> images = new LinkedHashSet<>();
    /**
     * Mark info
     */
    private Mark mark;
    /**
     * Body info
     */
    private Body body;
    /**
     * Age info
     */
    private Age age;
    /**
     * Engine info
     */
    private Engine engine;
    /**
     * Chassis info
     */
    private Chassis chassis;

    /**
     * Creates Car object using map of given parameters.
     *
     * @param price  Car price.
     * @param seller Car seller object.
     * @param images Set of car images.
     * @param params Map of inner object parameters.
     * @return Car object.
     */
    public static Car from(int price, User seller, Set<Image> images, Map<String, String> params) {
        var car = new Car();
        car.setPrice(price);
        car.setSeller(seller);
        car.setMark(new Mark()
                .setModel(params.get(MARK_MODEL.v()))
                .setManufacturer(params.get(MARK_MANUFACTURER.v)));
        car.setBody(new Body()
                .setType(params.get(BODY_TYPE.v()))
                .setColor(params.get(BODY_COLOR.v())));
        car.setAge(new Age()
                .setMileage(Long.parseLong(params.get(AGE_MILEAGE.v())))
                .setManufactureYear(Integer.parseInt(params.get(AGE_MANUFACTURE_YEAR.v())))
                .setNewness(params.get(AGE_NEWNESS.v())));
        car.setEngine(new Engine()
                .setEngineType(params.get(ENGINE_TYPE.v()))
                .setEngineVolume(Integer.parseInt(params.get(ENGINE_VOLUME.v()))));
        car.setChassis(new Chassis()
                .setTransmissionType(params.get(CHASSIS_TRANSMISSION_TYPE.v())));
        images.forEach(img -> img.setCar(car));
        car.setImages(images);
        return car;
    }

    /**
     * Enumerates parameters needed to create a car.
     */
    public enum Params {
        MARK_MANUFACTURER("mark_manufacturer"),
        MARK_MODEL("mark_model"),
        BODY_COLOR("body_color"),
        BODY_TYPE("body_type"),
        AGE_MANUFACTURE_YEAR("age_manufactureYear"),
        AGE_MILEAGE("age_mileage"),
        AGE_NEWNESS("age_newness"),
        ENGINE_TYPE("engine_type"),
        ENGINE_VOLUME("engine_volume"),
        CHASSIS_TRANSMISSION_TYPE("chassis_transmissionType");
        /**
         * String value holded.
         */
        private String v;

        /**
         * Constructor.
         *
         * @param v Value to store.
         */
        Params(String v) {
            this.v = v;
        }

        /**
         * Returns string value holded inside.
         *
         * @return Inner value.
         */
        public String v() {
            return this.v;
        }
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
        Car car = (Car) o;
        return id == car.id
                && price == car.price
                && Objects.equals(mark, car.mark)
                && Objects.equals(body, car.body)
                && Objects.equals(age, car.age)
                && Objects.equals(engine, car.engine)
                && Objects.equals(chassis, car.chassis);
    }

    /**
     * Returns this object's hashcode.
     *
     * @return Object's hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, price, mark, body, age, engine, chassis);
    }

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Item{id=%d, price=%d, mark=%s, body=%s, age=%s, engine=%s, chassis=%s}",
                this.id, this.price, this.mark, this.body, this.age, this.engine, this.chassis);
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
    public Car setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Returns price.
     *
     * @return Value of price field.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Sets price value.
     *
     * @param price Value to set.
     */
    public Car setPrice(int price) {
        this.price = price;
        return this;
    }

    /**
     * Returns seller.
     *
     * @return Value of seller field.
     */
    public User getSeller() {
        return this.seller;
    }

    /**
     * Sets seller value.
     *
     * @param seller Value to set.
     */
    public Car setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    /**
     * Returns images.
     *
     * @return Value of images field.
     */
    public Set<Image> getImages() {
        return this.images;
    }

    /**
     * Sets images value.
     *
     * @param images Value to set.
     */
    public Car setImages(Set<Image> images) {
        this.images = images;
        return this;
    }

    /**
     * Returns mark.
     *
     * @return Value of mark field.
     */
    public Mark getMark() {
        return this.mark;
    }

    /**
     * Sets mark value.
     *
     * @param mark Value to set.
     */
    public Car setMark(Mark mark) {
        this.mark = mark;
        return this;
    }

    /**
     * Returns body.
     *
     * @return Value of body field.
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Sets body value.
     *
     * @param body Value to set.
     */
    public Car setBody(Body body) {
        this.body = body;
        return this;
    }

    /**
     * Returns age.
     *
     * @return Value of age field.
     */
    public Age getAge() {
        return this.age;
    }

    /**
     * Sets age value.
     *
     * @param age Value to set.
     */
    public Car setAge(Age age) {
        this.age = age;
        return this;
    }

    /**
     * Returns engine.
     *
     * @return Value of engine field.
     */
    public Engine getEngine() {
        return this.engine;
    }

    /**
     * Sets engine value.
     *
     * @param engine Value to set.
     */
    public Car setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    /**
     * Returns chassis.
     *
     * @return Value of chassis field.
     */
    public Chassis getChassis() {
        return this.chassis;
    }

    /**
     * Sets chassis value.
     *
     * @param chassis Value to set.
     */
    public Car setChassis(Chassis chassis) {
        this.chassis = chassis;
        return this;
    }

    /**
     * Item age bean.
     *
     * @author Aleksei Sapozhnikov (vermucht@gmail.com)
     * @version 0.1
     * @since 0.1
     */
    @Embeddable
    public static class Age {
        /**
         * Logger.
         */
        @SuppressWarnings("unused")
        private static final Logger LOG = LogManager.getLogger(Age.class);
        /**
         * Year of manufacturing (2003).
         */
        @Column(name = "age_manufacture_year")
        private int manufactureYear;
        /**
         * Item newness (new, used).
         */
        @Column(name = "age_newness")
        private String newness;
        /**
         * Item mileage, kilometers (150 000)
         */
        @Column(name = "age_mileage")
        private long mileage;

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
            Age age = (Age) o;
            return manufactureYear == age.manufactureYear
                    && mileage == age.mileage
                    && Objects.equals(newness, age.newness);
        }

        /**
         * Returns this object's hashcode.
         *
         * @return Object's hashcode.
         */
        @Override
        public int hashCode() {
            return Objects.hash(manufactureYear, newness, mileage);
        }

        /**
         * Returns current object state as String object.
         *
         * @return Current object state as String object.
         */
        @Override
        public String toString() {
            return String.format(
                    "Age{manufactureYear=%d, newness='%s', mileage=%d}",
                    this.manufactureYear, this.newness, this.mileage);
        }

        /* * * * * * * * * * * *
         * getters and setters
         * * * * * * * * * * * */

        /**
         * Returns manufactureYear.
         *
         * @return Value of manufactureYear field.
         */
        public int getManufactureYear() {
            return this.manufactureYear;
        }

        /**
         * Sets manufactureYear value.
         *
         * @param manufactureYear Value to set.
         */
        public Age setManufactureYear(int manufactureYear) {
            this.manufactureYear = manufactureYear;
            return this;
        }

        /**
         * Returns newness.
         *
         * @return Value of newness field.
         */
        public String getNewness() {
            return this.newness;
        }

        /**
         * Sets newness value.
         *
         * @param newness Value to set.
         */
        public Age setNewness(String newness) {
            this.newness = newness;
            return this;
        }

        /**
         * Returns mileage.
         *
         * @return Value of mileage field.
         */
        public long getMileage() {
            return this.mileage;
        }

        /**
         * Sets mileage value.
         *
         * @param mileage Value to set.
         */
        public Age setMileage(long mileage) {
            this.mileage = mileage;
            return this;
        }
    }

    /**
     * TCar body bean.
     *
     * @author Aleksei Sapozhnikov (vermucht@gmail.com)
     * @version 0.1
     * @since 0.1
     */
    @Embeddable
    public static class Body {
        /**
         * Logger.
         */
        @SuppressWarnings("unused")
        private static final Logger LOG = LogManager.getLogger(Body.class);
        /**
         * Body type (sedan, track, SUV)
         */
        @Column(name = "body_type")
        private String type;
        /**
         * Body color (black, white, red)
         */
        @Column(name = "body_color")
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
            return Objects.equals(type, body.type)
                    && Objects.equals(color, body.color);
        }

        /**
         * Returns this object's hashcode.
         *
         * @return Object's hashcode.
         */
        @Override
        public int hashCode() {
            return Objects.hash(type, color);
        }

        /**
         * Returns current object state as String object.
         *
         * @return Current object state as String object.
         */
        @Override
        public String toString() {
            return String.format(
                    "Body{type='%s', color='%s'}",
                    this.type, this.color);
        }

        /* * * * * * * * * * * *
         * getters and setters
         * * * * * * * * * * * */

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

    /**
     * Item chassis bean.
     *
     * @author Aleksei Sapozhnikov (vermucht@gmail.com)
     * @version 0.1
     * @since 0.1
     */
    @Embeddable
    public static class Chassis {
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

    /**
     * Item engine bean.
     *
     * @author Aleksei Sapozhnikov (vermucht@gmail.com)
     * @version 0.1
     * @since 0.1
     */
    @Embeddable
    public static class Engine {
        /**
         * Logger.
         */
        @SuppressWarnings("unused")
        private static final Logger LOG = LogManager.getLogger(Engine.class);
        /**
         * Engine type (gasoline, kerosene, gas)
         */
        @Column(name = "engine_type")
        private String engineType;
        /**
         * Engine volume, liters (1.2)
         */
        @Column(name = "engine_volume")
        private int engineVolume;

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
            Engine engine = (Engine) o;
            return engineVolume == engine.engineVolume
                    && Objects.equals(engineType, engine.engineType);
        }

        /**
         * Returns this object's hashcode.
         *
         * @return Object's hashcode.
         */
        @Override
        public int hashCode() {
            return Objects.hash(engineType, engineVolume);
        }

        /**
         * Returns current object state as String object.
         *
         * @return Current object state as String object.
         */
        @Override
        public String toString() {
            return String.format(
                    "Engine{engineType='%s', engineVolume=%d}",
                    this.engineType, this.engineVolume);
        }

        /* * * * * * * * * * * *
         * getters and setters
         * * * * * * * * * * * */

        /**
         * Returns engineType.
         *
         * @return Value of engineType field.
         */
        public String getEngineType() {
            return this.engineType;
        }

        /**
         * Sets engineType value.
         *
         * @param engineType Value to set.
         */
        public Engine setEngineType(String engineType) {
            this.engineType = engineType;
            return this;
        }

        /**
         * Returns engineVolume.
         *
         * @return Value of engineVolume field.
         */
        public int getEngineVolume() {
            return this.engineVolume;
        }

        /**
         * Sets engineVolume value.
         *
         * @param engineVolume Value to set.
         */
        public Engine setEngineVolume(int engineVolume) {
            this.engineVolume = engineVolume;
            return this;
        }
    }

    /**
     * Item mark bean.
     *
     * @author Aleksei Sapozhnikov (vermucht@gmail.com)
     * @version 0.1
     * @since 0.1
     */
    @Embeddable
    public static class Mark {
        /**
         * Logger.
         */
        @SuppressWarnings("unused")
        private static final Logger LOG = LogManager.getLogger(Mark.class);
        /**
         * Manufacturer (Ford, Mercedes)
         */
        @Column(name = "mark_manufacturer")
        private String manufacturer;
        /**
         * Model (FX-1800, AMG-380)
         */
        @Column(name = "mark_model")
        private String model;

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
            Mark mark = (Mark) o;
            return Objects.equals(manufacturer, mark.manufacturer)
                    && Objects.equals(model, mark.model);
        }

        /**
         * Returns this object's hashcode.
         *
         * @return Object's hashcode.
         */
        @Override
        public int hashCode() {
            return Objects.hash(manufacturer, model);
        }

        /**
         * Returns current object state as String object.
         *
         * @return Current object state as String object.
         */
        @Override
        public String toString() {
            return String.format(
                    "Mark{manufacturer='%s', model='%s'}",
                    this.manufacturer, this.model);
        }

        /* * * * * * * * * * * *
         * getters and setters
         * * * * * * * * * * * */

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
}
