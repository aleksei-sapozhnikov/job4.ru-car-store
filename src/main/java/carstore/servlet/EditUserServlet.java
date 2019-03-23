package carstore.servlet;

import carstore.constants.ConstContext;
import carstore.model.User;
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
import java.util.HashMap;

/**
 * Servlet to add and edit users.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet({"/addUser", "/editUser"})
public class EditUserServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(EditUserServlet.class);

    private final Gson gson = new Gson();
    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        var context = this.getServletContext();
        this.factory = (SessionFactory)
                context.getAttribute(ConstContext.SESSION_FACTORY.v());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view" + "/editUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var values = new HashMap<String, String>();
        values.put("userId", req.getParameter("user_id"));
        values.put("login", req.getParameter("user_login"));
        values.put("password", req.getParameter("user_password"));
        values.put("phone", req.getParameter("user_phone"));
        long userId = 0;
        var strUserId = values.get("userId");
        if (strUserId != null && strUserId.matches("\\d+")) {
            userId = Long.parseLong(strUserId);
        }
        try (var session = this.factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                var user = session.get(User.class, userId);
                if (user == null) {
                    user = new User();
                }
                user.setLogin(values.get("login"));
                user.setPassword(values.get("password"));
                user.setPhone(values.get("phone"));
                session.saveOrUpdate(user);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        resp.sendRedirect(this.getServletContext().getContextPath());
    }
}
