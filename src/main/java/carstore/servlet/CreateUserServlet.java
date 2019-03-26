package carstore.servlet;

import carstore.constants.ConstContext;
import carstore.model.User;
import carstore.store.NewUserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
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
     * Utils to perform database transactions.
     */
    private NewUserStore userStore;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.userStore = (NewUserStore) ctx.getAttribute(ConstContext.USER_STORE.v());
    }

    /**
     * Shows form to add or edit user.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view" + "/createUser.jsp").forward(req, resp);
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
        var saved = this.userStore.saveIfNotExists(user);
        if (saved) {
            var resultMsg = String.format("User (%s) created", user.getLogin());
            resp.sendRedirect(String.format("%s?success=%s", req.getContextPath(), resultMsg));
        } else {
            req.setAttribute("error", String.format("Login (%s) already exists", user.getLogin()));
            this.doGet(req, resp);
        }
    }

    private User createUser(HttpServletRequest req) {
        var login = req.getParameter("user_login");
        var password = req.getParameter("user_password");
        var phone = req.getParameter("user_phone");
        return User.of(login, password, phone);
    }
}
