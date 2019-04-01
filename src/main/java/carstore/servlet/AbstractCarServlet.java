package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.factory.CarFactory;
import carstore.factory.ImageFactory;
import carstore.model.Car;
import carstore.model.User;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import carstore.store.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig
public abstract class AbstractCarServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AbstractCarServlet.class);
    /**
     * Car store.
     */
    protected CarStore carStore;
    /**
     * User store.
     */
    protected UserStore userStore;
    /**
     * Image store.
     */
    protected ImageStore imageStore;
    /**
     * Factory to create car from request parameters.
     */
    protected CarFactory carFactory;
    /**
     * Factory to get image set from request parameters.
     */
    protected ImageFactory imageFactory;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.carStore = (CarStore) ctx.getAttribute(Attributes.ATR_CAR_STORE.v());
        this.userStore = (UserStore) ctx.getAttribute(Attributes.ATR_USER_STORE.v());
        this.imageStore = (ImageStore) ctx.getAttribute(Attributes.ATR_IMAGE_STORE.v());
        this.carFactory = (CarFactory) ctx.getAttribute(Attributes.ATR_CAR_FACTORY.v());
        this.imageFactory = (ImageFactory) ctx.getAttribute(Attributes.ATR_IMAGE_FACTORY.v());
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
        var loggedUserId = this.getLoggedUserId(req);
        var loggedUser = this.getLoggedUser(req);
        var images = this.imageFactory.createImageSet(req);
        var carToSave = this.carFactory.createCar(req, loggedUser);
        this.validateChange(carToSave, loggedUserId);
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        this.carStore.saveOrUpdate(carToSave, images).accept(hbSession);
        return carToSave;
    }

    /**
     * Checks if logged user iw car's owner.
     * This is for the case when logged user passed the filter to edit car
     * but somehow changed car id parameter in edit car form.
     *
     * @param car          Car to update.
     * @param loggedUserId Id of user currently logged in session.
     * @throws ServletException If logged user is not car's owner.
     */
    private void validateChange(Car car, Long loggedUserId) throws ServletException {
        var carOwner = car.getOwner();
        if (carOwner.getId() != loggedUserId) {
            throw new ServletException(String.format(
                    "Logged user (id=%s) trying to change car (id=%s) but the user is not the car's owner",
                    loggedUserId, car.getId()
            ));
        }
    }

    /**
     * Returns id of the user logged in session.
     *
     * @param req Request object.
     * @return Logged user id.
     */
    private Long getLoggedUserId(HttpServletRequest req) {
        var httpSession = req.getSession(false);
        return (Long) httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
    }

    /**
     * Returns stored user by request parameters.
     *
     * @param req Request object.
     * @return Found persistent user.
     */
    private User getLoggedUser(HttpServletRequest req) {
        var loggedId = this.getLoggedUserId(req);
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        return this.userStore.get(loggedId).apply(hbSession);
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
                .append(req.getContextPath().equals("") ? "/" : req.getContextPath())
                .append("?")
                .append(WebApp.MSG_SUCCESS.v()).append("=").append(resultMsg)
                .toString();
        resp.sendRedirect(redirectPath);
    }
}



