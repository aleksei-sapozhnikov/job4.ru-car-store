package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.factory.CarFactory;
import carstore.factory.ImageFactory;
import carstore.model.Car;
import carstore.model.User;
import carstore.store.CarStore;
import carstore.store.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
@WebServlet(urlPatterns = {"/addCar", "/editCar"})
@MultipartConfig
public class AddCarServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AddCarServlet.class);
    /**
     * Car store.
     */
    private CarStore carStore;
    /**
     * User store.
     */
    private UserStore userStore;
    /**
     * Factory to create car from request parameters.
     */
    private CarFactory carFactory;
    /**
     * Factory to get image set from request parameters.
     */
    private ImageFactory imageFactory;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.carStore = (CarStore) ctx.getAttribute(Attributes.ATR_CAR_STORE.v());
        this.userStore = (UserStore) ctx.getAttribute(Attributes.ATR_USER_STORE.v());
        this.carFactory = (CarFactory) ctx.getAttribute(Attributes.ATR_CAR_FACTORY.v());
        this.imageFactory = (ImageFactory) ctx.getAttribute(Attributes.ATR_IMAGE_FACTORY.v());
    }

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
        var carIdStr = req.getParameter("carId");
        if (carIdStr != null) {
            var carId = Utils.parseLong(carIdStr, -1);
            var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
            var car = this.carStore.get(carId).apply(hbSession);
            req.setAttribute("editCar", car);
        }
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

    /**
     * Creates car object from request parameters and adds it to store.
     * If car (defined by id) existed, it is updated.
     *
     * @param req Request object.
     * @return Saved car.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
    private Car saveOrUpdateCar(HttpServletRequest req) throws IOException, ServletException {
        var owner = this.getLoggedUser(req);
        var images = this.imageFactory.createImageSet(req);
        var car = this.carFactory.createCar(req, owner);
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        this.carStore.saveOrUpdate(car, images).accept(hbSession);
        return car;
    }

    /**
     * Returns stored user by request parameters.
     *
     * @param req Request object.
     * @return Found persistent user.
     */
    private User getLoggedUser(HttpServletRequest req) {
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var httpSession = req.getSession(false);
        var userId = (Long) httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        return this.userStore.get(userId).apply(hbSession);
    }

    /**
     * Redirects to main page with given success message.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws IOException In case of problems.
     */
    private void redirectSuccess(HttpServletRequest req, HttpServletResponse resp, String resultMsg) throws IOException {
        var redirectPath = new StringBuilder()
                .append(req.getContextPath())
                .append("?")
                .append(WebApp.MSG_SUCCESS.v()).append("=").append(resultMsg)
                .toString();
        resp.sendRedirect(redirectPath);
    }
}
