package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utilities for common methods.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class Utils {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(Utils.class);

    /**
     * Reads fully given input stream to output stream.
     *
     * @param in  Input stream object.
     * @param out Output stream object.
     * @throws IOException In case of problems.
     */
    public static void readFullInput(InputStream in, OutputStream out) throws IOException {
        var buf = new byte[1024];
        var read = in.read(buf);
        while (read > -1) {
            out.write(buf, 0, read);
            read = in.read(buf);
        }
    }
}
