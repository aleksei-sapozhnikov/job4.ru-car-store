package carstore.experiments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Stores sample images.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class StoreImg {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(StoreImg.class);

    public static void main(String[] args) {
        // Save images to db
        try (var factory = new Configuration().configure().buildSessionFactory()) {
            try (var session = factory.openSession()) {
                var tx = session.beginTransaction();
                // save image
                List<byte[]> allData = Arrays.asList(
                        Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\1\\1.png")),
                        Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\1\\2.jpg")),
                        Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\1\\3.png")),
                        Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\1\\4.jpg")),
                        Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\1\\5.jpg")),
                        Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\1\\6.jpg")),
                        Files.readAllBytes(Path.of("C:\\Users\\Андрей\\Desktop\\1\\7.jpg"))
                );
                for (var data : allData) {
                    session.save(new Image().setData(data));
                }
                tx.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
