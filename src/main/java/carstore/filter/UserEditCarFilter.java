package carstore.filter;

import carstore.constants.ServletContextAttributes;
import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

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
@WebFilter({"/addCar", "/editCar"})
public class UserEditCarFilter implements Filter {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(UserEditCarFilter.class);

    private SessionFactory factory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        var context = filterConfig.getServletContext();
        this.factory = (SessionFactory)
                context.getAttribute(ServletContextAttributes.SESSION_FACTORY.v());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        boolean passed = false;

        long carStoreId = 0;
        var strStoreId = req.getParameter("storeId");
        if (strStoreId != null && strStoreId.matches("\\d+")) {
            carStoreId = Long.parseLong(strStoreId);
        }

        var session = req.getSession();
        var loggedUser = session.getAttribute("loggedUser");
        if (loggedUser != null) {
            try (var hbSession = this.factory.openSession()) {
                var tx = hbSession.beginTransaction();
                try {
                    var finalCarStoreId = carStoreId;
                    var user = hbSession.get(User.class, ((User) loggedUser).getId());
                    var carsFound = user.getCars().stream()
                            .filter(c -> c.getId() == finalCarStoreId)
                            .count();
                    if (carsFound == 1) {
                        passed = true;
                    }
                    tx.rollback();
                } catch (Exception e) {
                    tx.rollback();
                    throw e;
                }
            }
        }
        if (passed) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath());
        }
    }

    @Override
    public void destroy() {

    }
}
