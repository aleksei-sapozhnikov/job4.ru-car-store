package carstore.experiments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Image servlet
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
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
            var image = session.get(Image.class, id);
            try (var out = resp.getOutputStream()) {
                out.write(image.getData());
            }
            tx.rollback();
        }
    }
}
