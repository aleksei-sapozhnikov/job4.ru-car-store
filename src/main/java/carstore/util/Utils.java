package carstore.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

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
        if (in != null) {
            var buf = new byte[1024];
            var read = in.read(buf);
            while (read > -1) {
                out.write(buf, 0, read);
                read = in.read(buf);
            }
        }
    }

    /**
     * Parses id from string if it's possible.
     *
     * @param string          Given string.
     * @param notParsedReturn Value to return if could not parse.
     * @return Parsed id or <tt>notFound</tt> value.
     */
    public static long parseLong(String string, long notParsedReturn) {
        long result = notParsedReturn;
        try {
            result = Long.parseLong(string);
        } catch (Exception e) {
            LOG.error(String.format("Could not parse value (%s) as long", string), e);
        }
        return result;
    }

    /**
     * Reads and returns resource as byte array,
     *
     * @param callerClass Object which is calling the resource (.class object).
     * @param path        Resource path.
     * @return Resource as byte array.
     */
    public static byte[] readResource(Class<?> callerClass, String path) {
        byte[] result = new byte[0];
        try (var in = callerClass.getClassLoader().getResourceAsStream(path);
             var out = new ByteArrayOutputStream()) {
            if (in == null) {
                throw new NullPointerException("Input resource is null");
            }
            Utils.readFullInput(in, out);
            result = out.toByteArray();
        } catch (Exception e) {
            LOG.fatal(e.getMessage(), e);
        }
        return result;
    }

    public static String readString(Part part) throws IOException {
        String result;
        try (var in = part.getInputStream();
             var out = new ByteArrayOutputStream()) {
            Utils.readFullInput(in, out);
            result = new String(out.toByteArray(), StandardCharsets.UTF_8);
        }
        return result;
    }

    public static Integer readInteger(Part part) throws IOException, ServletException {
        String str = Utils.readString(part);
        Integer result;
        try {
            result = Integer.valueOf(str);
        } catch (NumberFormatException e) {
            throw new ServletException(String.format(
                    "Given parameter (%s) cannot be parsed as Integer value", str), e);
        }
        return result;
    }

    public static Long readLong(Part part) throws IOException, ServletException {
        String str = Utils.readString(part);
        Long result;
        try {
            result = Long.valueOf(str);
        } catch (NumberFormatException e) {
            throw new ServletException(String.format(
                    "Given parameter (%s) cannot be parsed as Long value", str), e);
        }
        return result;
    }


    public static byte[] readByteArray(Part part) throws IOException {
        byte[] result;
        try (var in = part.getInputStream();
             var out = new ByteArrayOutputStream()) {
            Utils.readFullInput(in, out);
            result = out.toByteArray();
        }
        return result;
    }
}
