package carstore.servlet.car;

import carstore.model.Image;
import carstore.model.car.Car;
import carstore.store.NewUserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * General servlet for Car operations.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@MultipartConfig
public abstract class AbstractCarServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AbstractCarServlet.class);

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
    }

    protected Car createCarFromParameters(HttpServletRequest req) throws IOException, ServletException {
        var session = (Session) req.getAttribute("hibernateSession");
        var userStore = new NewUserStore(session);

        var loggedUserId = (long) req.getSession().getAttribute("loggedUserId");
        var user = userStore.get(loggedUserId);
        var values = new HashMap<String, String>();
        var images = new HashSet<Image>();
        this.fillParameters(req, values, images);
        return Car.of(user, images, values);
    }

    protected void fillParameters(HttpServletRequest req, Map<String, String> values, Set<Image> images) throws IOException, ServletException {
        for (var part : req.getParts()) {
            try (var in = part.getInputStream();
                 var out = new ByteArrayOutputStream()) {
                Utils.readFullInput(in, out);
                var name = part.getName();
                if (name.startsWith("image")) {
                    images.add(new Image().setData(out.toByteArray()));
                } else {
                    values.put(part.getName(), out.toString().intern());
                }
            }
        }
    }


}
