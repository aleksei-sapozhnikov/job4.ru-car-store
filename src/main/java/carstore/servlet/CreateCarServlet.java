package carstore.servlet;

import carstore.constants.ConstContext;
import carstore.model.Image;
import carstore.model.User;
import carstore.model.car.Car;
import carstore.store.NewCarStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Controls adding car.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/addCar")
@MultipartConfig
public class CreateCarServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CreateCarServlet.class);
    /**
     * Hibernate session factory.
     */
    private SessionFactory hbFactory;
    /**
     * Utils to perform database transactions.
     */
    private NewCarStore carStore;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.hbFactory = (SessionFactory) ctx.getAttribute(ConstContext.SESSION_FACTORY.v());
        this.carStore = (NewCarStore) ctx.getAttribute(ConstContext.CAR_STORE.v());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view" + "/createCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (User) req.getSession().getAttribute("loggedUser");
        var values = new HashMap<String, String>();
        var images = new HashSet<Image>();
        this.fillParametersMaps(req, values, images);
        var car = Car.from(Integer.parseInt(values.get("price")), user, images, values);
        this.carStore.save(car);
        resp.sendRedirect(this.getServletContext().getContextPath() + "?id=" + car.getId());
    }


    private void fillParametersMaps(HttpServletRequest req, Map<String, String> values, Set<Image> images) throws IOException, ServletException {
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
