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
        this.carFactory = (CarFactory) ctx.getAttribute(Attributes.ATR_CAR_FACTORY.v());
        this.imageFactory = (ImageFactory) ctx.getAttribute(Attributes.ATR_IMAGE_FACTORY.v());
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
    protected Car saveOrUpdateCar(HttpServletRequest req) throws IOException, ServletException {
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
    protected User getLoggedUser(HttpServletRequest req) {
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
    protected void redirectSuccess(HttpServletRequest req, HttpServletResponse resp, String resultMsg) throws IOException {
        var redirectPath = new StringBuilder()
                .append(req.getContextPath().equals("") ? "/" : req.getContextPath())
                .append("?")
                .append(WebApp.MSG_SUCCESS.v()).append("=").append(resultMsg)
                .toString();
        resp.sendRedirect(redirectPath);
    }
}



