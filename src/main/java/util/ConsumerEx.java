package util;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Function interface. It take a single param.
 *
 * @param <T> type.
 */
@FunctionalInterface
public interface ConsumerEx<T> {
    /**
     * Accept arg.
     *
     * @param param arg
     * @throws ServletException possible exception.
     */
    void accept(T param) throws ServletException, IOException;
}