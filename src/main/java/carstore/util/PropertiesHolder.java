package carstore.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


/**
 * Loads properties and returns list of values corresponding to given start key.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class PropertiesHolder {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(PropertiesHolder.class);

    /**
     * Lists of properties with certain property values
     * which will be returned by getters.
     */
    private final List<String> manufacturer, newness, bodyType, color, engineFuel, transmissionType;

    /**
     * Constructs new instance.
     * Loads parameters from properties resource file to values map.
     *
     * @param resourcePath Path to Resource file with properties.
     */
    public PropertiesHolder(String resourcePath) {
        Properties properties = new Properties();
        try {
            properties = this.loadProperties(this.getClass(), resourcePath);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        this.manufacturer = this.buildList("manufacturer.", properties);
        this.newness = this.buildList("newness.", properties);
        this.bodyType = this.buildList("bodyType.", properties);
        this.color = this.buildList("color.", properties);
        this.engineFuel = this.buildList("engineFuel.", properties);
        this.transmissionType = this.buildList("transmissionType.", properties);
    }

    /**
     * Returns list of values which have key starting with given string.
     *
     * @param keyBeginning Key must start with this.
     * @param properties   Properties object to get key/value from.
     * @return List of values.
     */
    private List<String> buildList(String keyBeginning, Properties properties) {
        return properties.stringPropertyNames().stream()
                .filter(key -> key.startsWith(keyBeginning))
                .map(properties::getProperty).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Returns manufacturer.
     *
     * @return Value of manufacturer field.
     */
    public List<String> getManufacturer() {
        return this.manufacturer;
    }

    /**
     * Returns newness.
     *
     * @return Value of newness field.
     */
    public List<String> getNewness() {
        return this.newness;
    }

    /**
     * Returns bodyType.
     *
     * @return Value of bodyType field.
     */
    public List<String> getBodyType() {
        return this.bodyType;
    }

    /**
     * Returns color.
     *
     * @return Value of color field.
     */
    public List<String> getColor() {
        return this.color;
    }

    /**
     * Returns engineFuel.
     *
     * @return Value of engineFuel field.
     */
    public List<String> getEngineFuel() {
        return this.engineFuel;
    }

    /**
     * Returns transmissionType.
     *
     * @return Value of transmissionType field.
     */
    public List<String> getTransmissionType() {
        return this.transmissionType;
    }

    /**
     * Loads properties as resource.
     *
     * @param clazz        Class file of object loading the resource.
     * @param resourcePath Path to the properties file resource,
     *                     e.g: "ru/job4j/vacancies/main.properties"
     * @return Properties object with values read from file.
     */
    private Properties loadProperties(Class<?> clazz, String resourcePath) throws IOException {
        Properties props = new Properties();
        ClassLoader loader = clazz.getClassLoader();
        try (InputStream input = loader.getResourceAsStream(resourcePath)) {
            props.load(input);
        }
        return props;
    }
}