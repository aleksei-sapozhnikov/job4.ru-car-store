package carstore.experiments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Image servlet
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,   // 10 MB
        maxFileSize = 1024 * 1024 * 50,         // 50 MB
        maxRequestSize = 1024 * 1024 * 100)     // 100 MB
public class ImageServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(ImageServlet.class);

    private SessionFactory factory;

    public void init() {
        this.factory = new Configuration().configure().buildSessionFactory();

    }

    public void destroy() {
        this.factory.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var idParam = req.getParameter("id");
        var id = Integer.parseInt(idParam);
        try (var session = this.factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                var image = session.get(Image.class, id);
                try (var out = resp.getOutputStream()) {
                    out.write(image.getData());
                }
                tx.rollback();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // read data
        byte[] data;
        req.setCharacterEncoding("UTF-8");
        try (var out = new ByteArrayOutputStream()) {
            var buf = new byte[1024];
            var photo = req.getPart("photo");
            try (var in = photo.getInputStream()) {
                var read = in.read(buf);
                while (read > -1) {
                    out.write(buf, 0, read);
                    read = in.read(buf);
                }
            }
            data = out.toByteArray();
        }

        var image = new Image().setData(data);
        // save object
        int id;
        try (
                var session = this.factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                session.save(image);
                id = image.getId();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        try (
                var writer = resp.getWriter()) {
            writer.print(id);
        }
    }
}
