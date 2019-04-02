package carstore.filter;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.Car;
import carstore.store.CarStore;
import carstore.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Checks logged user and his ability to edit given car.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebFilter(filterName = "UserCanEditCarFilter")
public class UserCanEditCarFilter implements Filter {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(UserCanEditCarFilter.class);

    private CarStore carStore;

    /**
     * Initializes on filter creation.
     *
     * @param filterConfig Filter config object.
     */
    @Override
    public void init(FilterConfig filterConfig) {
        var ctx = filterConfig.getServletContext();
        this.carStore = (CarStore) ctx.getAttribute(Attributes.ATR_CAR_STORE.v());
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        var userId = this.getLoggedUserId(req);
        var car = this.getCarFromStorage(req);
        var owner = car.getOwner();
        if (userId == owner.getId()) {
            chain.doFilter(req, response);
        } else {
            var errorMsg = "You are not allowed to edit this car";
            var redirectPath = new StringBuilder()
                    .append((String) req.getServletContext().getAttribute(Attributes.ATR_CONTEXT_PATH.v()))
                    .append("?")
                    .append(WebApp.MSG_ERROR.v()).append("=").append(errorMsg)
                    .toString();
            resp.sendRedirect(redirectPath);
        }
    }

    /**
     * Returns user id stored in session.
     *
     * @param req Request object.
     * @return Logged user id.
     */
    private Long getLoggedUserId(HttpServletRequest req) {
        var session = req.getSession(false);
        return (Long) (session.getAttribute(Attributes.ATR_LOGGED_USER_ID.v()));
    }

    /**
     * Returns car from store using request parameter.
     *
     * @param request Request object.
     * @return Car found in storage.
     * @throws ServletException If car id parameter not found or could not be parsed as number.
     *                          If car was not found in storage.
     */
    private Car getCarFromStorage(ServletRequest request) throws ServletException {
        var carIdStr = request.getParameter(Attributes.PRM_CAR_ID.v());
        var carId = Utils.parseLong(carIdStr, -1);
        if (carId == -1) {
            throw new ServletException(String.format(
                    "Car id: parameter (%s) not found or could not be parsed as number.", carIdStr));
        }
        var session = (Session) request.getAttribute(Attributes.ATR_HB_SESSION.v());
        var car = this.carStore.get(carId).apply(session);
        if (car == null) {
            throw new ServletException(String.format(
                    "Car with given id (%s) not found in storage.", carId));
        }
        return car;
    }

    /**
     * Is called on filter destroy.
     */
    @Override
    public void destroy() {
    }
}
