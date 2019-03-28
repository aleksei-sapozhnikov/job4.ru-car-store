package carstore.filter;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.Car;
import carstore.store.CarStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import util.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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

    /**
     * Initializes on filter creation.
     *
     * @param filterConfig Filter config object.
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Car car = this.getCarFromStorage(request);
        if (12 == car.getOwner().getId()) {
            chain.doFilter(request, response);
        } else {
            var errorMsg = String.format("User (%s) is not allowed to edit this car", car.getOwner().getLogin());
            var redirectPath = new StringBuilder()
                    .append(WebApp.BASEDIR.v())
                    .append("?")
                    .append(WebApp.ERROR_MSG).append(errorMsg)
                    .toString();
            var resp = (HttpServletResponse) response;
            resp.sendRedirect(redirectPath);
        }
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
        var carIdStr = request.getParameter(Attributes.CAR_STORE_ID.v());
        var carId = Utils.parseLong(carIdStr, -1);
        if (carId == -1) {
            throw new ServletException(String.format(
                    "Car id: parameter (%s) not found or could not be parsed as number.", carIdStr));
        }
        var session = (Session) request.getAttribute(Attributes.HB_SESSION.v());
        var car = new CarStore().get(carId).apply(session);
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
