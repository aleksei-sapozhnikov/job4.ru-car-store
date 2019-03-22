package carstore.servlet;

import carstore.constants.ServletContextAttributes;
import carstore.model.Image;
import carstore.model.User;
import carstore.model.car.*;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Controls adding and editing car.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet({"/addCar", "/editCar"})
@MultipartConfig
public class EditCarServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(EditCarServlet.class);
    private final Gson gson = new Gson();
    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        var context = this.getServletContext();
        this.factory = (SessionFactory)
                context.getAttribute(ServletContextAttributes.SESSION_FACTORY.v());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var carId = req.getParameter("storeId");
        if (carId != null && carId.matches("\\d+")) {
            try (var session = this.factory.openSession()) {
                var tx = session.beginTransaction();
                try {
                    var editCar = session.get(Car.class, Long.valueOf(carId));
                    if (editCar != null) {
                        req.setAttribute("editCar", editCar);
                    }
                    req.getRequestDispatcher("/WEB-INF/view" + "/editCar.jsp").forward(req, resp);
                    tx.rollback();
                } catch (Exception e) {
                    tx.rollback();
                    throw e;
                }
            }
        } else {
            req.getRequestDispatcher("/WEB-INF/view" + "/editCar.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = ((User) req.getSession().getAttribute("loggedUser")).getId();

        var values = new TreeMap<String, String>();
        var images = new ArrayList<Image>();
        this.fillParametersMaps(req, values, images);
        long carStoreId = 0;
        var strStoreId = values.get("storeId");
        if (strStoreId != null && strStoreId.matches("\\d+")) {
            carStoreId = Long.parseLong(strStoreId);
        }
        long savedId;
        try (var hbSession = this.factory.openSession()) {
            var tx = hbSession.beginTransaction();
            try {
                var user = hbSession.get(User.class, userId);
                final long finalCarStoreId = carStoreId;
                Car destCar = user.getCars().stream()
                        .filter(c -> c.getId() == finalCarStoreId)
                        .findFirst()
                        .orElseGet(() -> {
                            var c = new Car();
                            user.getCars().add(c);
                            return c;
                        });
                this.setCarParameters(destCar, values);
                destCar.setSeller(user);
                this.setCarImages(images, hbSession, destCar);

                hbSession.saveOrUpdate(destCar);

                savedId = destCar.getId();
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        resp.sendRedirect(this.getServletContext().getContextPath() + "?id=" + savedId);
    }

    private void setCarImages(ArrayList<Image> images, Session hbSession, Car destCar) {
        if (images.size() > 0 && images.get(0).getData().length > 0) {
            for (var img : images) {
                img.setCar(destCar);
                hbSession.save(img);
            }
        }
    }

    private void setCarParameters(Car car, Map<String, String> values) {
        car.setMark(new Mark()
                .setManufacturer(values.get("mark_manufacturer"))
                .setModel(values.get("mark_model")));
        car.setAge(new Age()
                .setMileage(Long.parseLong(values.get("age_mileage")))
                .setManufactureYear(Integer.parseInt(values.get("age_manufactureYear")))
                .setNewness(values.get("age_newness")));
        car.setBody(new Body()
                .setColor(values.get("body_color"))
                .setType(values.get("body_type")));
        car.setChassis(new Chassis()
                .setTransmissionType(values.get("chassis_type")));
        car.setEngine(new Engine()
                .setEngineType(values.get("engine_type"))
                .setEngineVolume(Integer.parseInt(values.get("engine_volume"))));
        car.setPrice(Integer.parseInt(values.get("price")));
    }

    private void fillParametersMaps(HttpServletRequest req, Map<String, String> values, List<Image> images) throws IOException, ServletException {
        for (var part : req.getParts()) {
            try (var in = part.getInputStream();
                 var out = new ByteArrayOutputStream()
            ) {
                var buffer = new byte[1024];
                var read = in.read(buffer);
                while (read > -1) {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }
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
