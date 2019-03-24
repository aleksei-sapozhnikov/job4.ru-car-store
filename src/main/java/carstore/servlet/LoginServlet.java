package carstore.servlet;

import carstore.constants.ConstContext;
import carstore.model.User;
import carstore.store.NewUserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

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
     * Hibernate session factory.
     */
    private SessionFactory hbFactory;
    /**
     * Utils to perform database transactions.
     */
    private NewUserStore userStore;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.hbFactory = (SessionFactory) ctx.getAttribute(ConstContext.SESSION_FACTORY.v());
        this.userStore = (NewUserStore) ctx.getAttribute(ConstContext.USER_STORE.v());
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
        req.getRequestDispatcher("WEB-INF/view" + "/loginUser.jsp").forward(req, resp);
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
//        var login = req.getParameter("login_login");
//        var password = req.getParameter("login_password");
//        try (var session = this.hbFactory.openSession()) {
//            var user = this.userStore.getByCredentials(session, login, password);
//            if (user != null) {
//                this.attachAndPass(req, resp, user);
//            } else {
//                this.forwardToLoginWithError(req, resp, login, password);
//            }
//        }
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
        req.setAttribute("error", String.format(
                "No user (login: %s, password: %s) found",
                login, password));
        this.doGet(req, resp);
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
        req.getSession().setAttribute("loggedUser", user);
        resp.sendRedirect(req.getContextPath());
    }
}
