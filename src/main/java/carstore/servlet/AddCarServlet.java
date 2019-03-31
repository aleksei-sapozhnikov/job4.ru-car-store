package carstore.servlet;

import carstore.constants.WebApp;
import carstore.model.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
@WebServlet(urlPatterns = {"/addCar"})
@MultipartConfig
public class AddCarServlet extends AbstractCarServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AddCarServlet.class);

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
        var resultMsg = String.format("Car (%s %s) saved.", car.getManufacturer(), car.getModel());
        this.redirectSuccess(req, resp, resultMsg);
    }
}