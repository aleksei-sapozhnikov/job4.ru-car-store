package carstore.filter;

import carstore.constants.ConstContext;
import carstore.model.User;
import carstore.store.NewCarStore;
import carstore.store.NewUserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Utils;

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

    private NewUserStore userStore;
    private NewCarStore carStore;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        var context = filterConfig.getServletContext();
        this.userStore = (NewUserStore) context.getAttribute(ConstContext.USER_STORE.v());
        this.carStore = (NewCarStore) context.getAttribute(ConstContext.CAR_STORE.v());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        var loggedUser = (User) req.getSession(false).getAttribute("loggedUser");
        var carStoreId = Utils.parseLong(req.getParameter("storeId"), -1);
        if (carStoreId == -1) {
            throw new ServletException("Id parameter not found");
        }
        var canEdit = this.carStore.containsSeller(loggedUser.getId());
        if (canEdit) {
            chain.doFilter(req, resp);
        } else {
            var msg = "You are not allowed to edit this car";
            resp.sendRedirect(String.format("%s/login?error=%s", req.getContextPath(), msg));
        }
    }

    @Override
    public void destroy() {

    }
}
