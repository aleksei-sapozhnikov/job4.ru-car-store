package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Car bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Car {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Car.class);

    /**
     * Unique id.
     */
    private int id;
    /**
     * Price
     */
    private int price;
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
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Car{id=%d, price=%d, mark=%s, body=%s, age=%s, engine=%s, chassis=%s}",
                this.id, this.price, this.mark, this.body, this.age, this.engine, this.chassis);
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
    public Car setId(int id) {
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
}
