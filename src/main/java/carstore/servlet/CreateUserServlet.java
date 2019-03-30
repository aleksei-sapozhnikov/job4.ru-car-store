package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.User;
import carstore.store.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to add and edit users.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/addUser")
public class CreateUserServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CreateUserServlet.class);
    /**
     * User store.
     */
    private UserStore userStore;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.userStore = (UserStore) ctx.getAttribute(Attributes.ATR_USER_STORE.v());
    }

    /**
     * Shows form to add user.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(
                String.join("/", WebApp.VIEW_ROOT.v(), WebApp.PG_CREATE_USER.v())
        ).forward(req, resp);
    }

    /**
     * Adds new user by given data from form.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = this.createUser(req);
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var saved = this.userStore.saveIfNotExists(user).apply(hbSession);
        if (saved) {
            var resultMsg = String.format("User (%s) created", user.getLogin());
            var redirectPath = new StringBuilder()
                    .append(req.getContextPath())
                    .append("?")
                    .append(WebApp.MSG_SUCCESS.v()).append("=").append(resultMsg)
                    .toString();
            resp.sendRedirect(redirectPath);
        } else {
            var resultMsg = String.format("Login (%s) already exists", user.getLogin());
            req.setAttribute(WebApp.MSG_ERROR.v(), resultMsg);
            this.doGet(req, resp);
        }
    }

    /**
     * Creates new user useing request parameters.
     *
     * @param req Request object.
     * @return User created.
     */
    private User createUser(ServletRequest req) {
        var login = req.getParameter(Attributes.PRM_USER_LOGIN.v());
        var password = req.getParameter(Attributes.PRM_USER_PASSWORD.v());
        var phone = req.getParameter(Attributes.PRM_USER_PHONE.v());
        return User.of(login, password, phone);
    }
}
