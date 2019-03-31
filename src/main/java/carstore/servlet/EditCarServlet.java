package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.Car;
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

/**
 * Controls adding car.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet(urlPatterns = {"/editCar"})
@MultipartConfig
public class EditCarServlet extends AbstractCarServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(EditCarServlet.class);

    /**
     * Shows form to add car.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var carIdStr = req.getParameter(Attributes.PRM_CAR_ID.v());
        var carId = Utils.parseLong(carIdStr, 0);
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var car = this.carStore.get(carId).apply(hbSession);
        req.setAttribute(Attributes.ATR_CAR_TO_EDIT.v(), car);
        req.getRequestDispatcher(
                String.join("/", WebApp.VIEW_ROOT.v(), WebApp.PG_CREATE_CAR.v())
        ).forward(req, resp);
    }

    /**
     * Saves or updates car and redirects with message.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Car car = this.saveOrUpdateCar(req);
        var resultMsg = String.format("Car (%s %s) updated.", car.getManufacturer(), car.getModel());
        this.redirectSuccess(req, resp, resultMsg);
    }
}
