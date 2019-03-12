package carstore.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Car engine bean.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Engine {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Engine.class);

    /**
     * Unique id.
     */
    private int id;

    /**
     * Engine type (gasoline, kerosene, gas)
     */
    private String engineType;
    /**
     * Engine volume, liters (1.2)
     */
    private int engineVolume;

    /**
     * Returns current object state as String object.
     *
     * @return Current object state as String object.
     */
    @Override
    public String toString() {
        return String.format(
                "Engine{id=%d, engineType='%s', engineVolume=%d}",
                this.id, this.engineType, this.engineVolume);
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
    public Engine setId(int id) {
        this.id = id;
        return this;
    }

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
