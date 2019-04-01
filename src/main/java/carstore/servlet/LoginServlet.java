package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.User;
import carstore.store.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Services log in by user-password form.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(LoginServlet.class);
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
     * Shows login form.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(
                String.join("/", WebApp.VIEW_ROOT.v(), WebApp.PG_LOGIN.v())
        ).forward(req, resp);
    }

    /**
     * Assigns user to session object if given login-password are right.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws IOException In case of problems.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        var login = req.getParameter(Attributes.PRM_USER_LOGIN.v());
        var password = req.getParameter(Attributes.PRM_USER_PASSWORD.v());
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var user = userStore.getByCredentials(login, password).apply(hbSession);
        if (user != null) {
            this.attachAndPass(req, resp, user);
        } else {
            this.forwardToLoginWithError(req, resp, login, password);
        }
    }

    /**
     * Attaches found user to session and
     * redirects back to application main page.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @param user User object to attach to session.
     * @throws IOException In case of problems.
     */
    private void attachAndPass(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        var loggedUserId = user.getId();
        req.getSession().setAttribute(Attributes.ATR_LOGGED_USER_ID.v(), loggedUserId);
        var msg = String.format("User (%s) logged in", user.getLogin());
        var redirectPath = new StringBuilder()
                .append("/")
                .append("?")
                .append(WebApp.MSG_SUCCESS.v()).append("=").append(msg)
                .toString();
        resp.sendRedirect(redirectPath);
    }

    /**
     * Forwards request back to login page
     * with attached error message.
     *
     * @param req      Request object.
     * @param resp     Response object.
     * @param login    Given login (for error message).
     * @param password Given password (for error message).
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    private void forwardToLoginWithError(HttpServletRequest req, HttpServletResponse resp, String login, String password) throws ServletException, IOException {
        req.setAttribute(
                WebApp.MSG_ERROR.v(),
                String.format("No user (login: %s, password: %s) found", login, password));
        this.doGet(req, resp);
    }
}
