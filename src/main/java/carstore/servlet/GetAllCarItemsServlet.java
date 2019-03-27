package carstore.servlet;

import carstore.constants.ConstContext;
import carstore.logic.Transformer;
import carstore.store.NewCarStore;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns list of stored objects in frontend-needed form.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/getAllCarItems")
public class GetAllCarItemsServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(GetAllCarItemsServlet.class);
    /**
     * Json parser/formatter.
     */
    private final Gson gson = new Gson();
    /**
     * Transformer of classes one-to-another.
     */
    private Transformer transformer;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var context = this.getServletContext();
        this.transformer = (Transformer) context.getAttribute(ConstContext.TRANSFORMER.v());
    }

    /**
     * Returns list of stored cars as JSON string of frontend Item objects.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws IOException In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var carStore = new NewCarStore((Session) req.getAttribute("hibernateSession"));
        var cars = carStore.getAll();
        var items = this.transformer.carToItem(cars);
        try (var writer = resp.getWriter()) {
            this.gson.toJson(items, writer);
        }
    }
}
