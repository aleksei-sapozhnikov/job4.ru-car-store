package carstore.servlet;

import carstore.constants.ServletContextAttributes;
import com.google.gson.Gson;
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
 * Servlet to log in.
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

    private final Gson gson = new Gson();
    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        var context = this.getServletContext();
        this.factory = (SessionFactory)
                context.getAttribute(ServletContextAttributes.SESSION_FACTORY.v());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view" + "/loginUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var login = req.getParameter("login_login");
        var password = req.getParameter("login_password");
        try (var session = this.factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                var found = session.createQuery("from User where login=:login and password=:password")
                        .setParameter("login", login)
                        .setParameter("password", password)
                        .list();
                if (found.size() == 1) {
                    var httpSession = req.getSession();
                    httpSession.setAttribute("loggedUser", found.get(0));
                }
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        resp.sendRedirect(this.getServletContext().getContextPath());
    }
}
