package carstore.servlet.car;

import carstore.model.Image;
import carstore.model.User;
import carstore.model.car.Car;
import carstore.store.NewCarStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static carstore.model.car.Car.Params.*;

/**
 * Controls adding car.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/editCar")
@MultipartConfig
public class EditCarServlet extends AbstractCarServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(EditCarServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var carStore = new NewCarStore((Session) req.getAttribute("hibernateSession"));
        long id = this.getId(req);
        var car = carStore.get(id);
        req.setAttribute("editCar", car);
        req.getRequestDispatcher("/WEB-INF/view" + "/editCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = (Session) req.getAttribute("hibernateSession");
        var carStore = new NewCarStore((Session) req.getAttribute("hibernateSession"));
        long id = this.getId(req);
        var tx = session.beginTransaction();
        var car = session.get(Car.class, id);

        // update car parameters
        var user = (User) req.getSession().getAttribute("loggedUser");
        var values = new HashMap<String, String>();
        var images = new HashSet<Image>();
        this.fillParameters(req, values, images);
        car.setPrice(Integer.parseInt(values.get("price")));
        car.setSeller(user);
        car.setMark(new Car.Mark()
                .setModel(values.get(MARK_MODEL.v()))
                .setManufacturer(values.get(MARK_MANUFACTURER.v())));
        car.setBody(new Car.Body()
                .setType(values.get(BODY_TYPE.v()))
                .setColor(values.get(BODY_COLOR.v())));
        car.setAge(new Car.Age()
                .setMileage(Long.parseLong(values.get(AGE_MILEAGE.v())))
                .setManufactureYear(Integer.parseInt(values.get(AGE_MANUFACTURE_YEAR.v())))
                .setNewness(values.get(AGE_NEWNESS.v())));
        car.setEngine(new Car.Engine()
                .setEngineType(values.get(ENGINE_TYPE.v()))
                .setEngineVolume(Integer.parseInt(values.get(ENGINE_VOLUME.v()))));
        car.setChassis(new Car.Chassis()
                .setTransmissionType(values.get(CHASSIS_TRANSMISSION_TYPE.v())));
        images.forEach(car::addImage);

        tx.commit();
        resp.sendRedirect(this.getServletContext().getContextPath() + "?id=" + car.getId());
    }

    private long getId(HttpServletRequest req) throws ServletException {
        var id = Utils.parseLong(req.getParameter("storeId"), -1);
        if (id == -1) {
            throw new ServletException("Could not parse id parameter");
        }
        return id;
    }


    private void setCarParameters(Car car, User seller, Set<Image> images, Map<String, String> values) {

    }

}
