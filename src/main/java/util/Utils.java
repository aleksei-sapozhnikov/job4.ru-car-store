package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;

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

    /**
     * Parses id from string if it's possible.
     *
     * @param string   Given string.
     * @param notFound Value to return if could not parse.
     * @return Parsed id or <tt>notFound</tt> value.
     */
    public static long parseLong(String string, long notFound) {
        long result = notFound;
        if (string != null && string.matches("\\d+")) {
            result = Long.parseLong(string);
        }
        return result;
    }

    public <T> T doTransactionWithCommit(SessionFactory factory, Function<Session, T> operations) {
        T result;
        try (var session = factory.openSession()) {
            result = this.doTransactionWithCommit(session, operations);
        }
        return result;
    }

    public <T> T doTransactionWithCommit(Session session, Function<Session, T> operations) {
        T result;
        var tx = session.beginTransaction();
        try {
            result = operations.apply(session);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        return result;
    }


}
