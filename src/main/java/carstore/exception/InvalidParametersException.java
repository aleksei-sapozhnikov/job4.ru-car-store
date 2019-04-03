package carstore.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Is thrown when given parameters are not suitable
 * to create a new object with them.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class InvalidParametersException extends Exception {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(InvalidParametersException.class);

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidParametersException(String message) {
        super(message);
    }
}
