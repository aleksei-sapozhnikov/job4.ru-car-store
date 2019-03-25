package carstore.servlet.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controls adding car.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/editCar")
@MultipartConfig
public class EditCarServlet extends AbstractCarServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(EditCarServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = this.getId(req);
        var car = this.getCarStore().get(id);
        req.setAttribute("editCar", car);
        req.getRequestDispatcher("/WEB-INF/view" + "/editCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = this.getId(req);
        var car = this.createCarFromParameters(req);
        car.setId(id);
        this.getCarStore().update(car);
        resp.sendRedirect(this.getServletContext().getContextPath() + "?id=" + car.getId());
    }

    private long getId(HttpServletRequest req) throws ServletException {
        var id = Utils.parseLong(req.getParameter("storeId"), -1);
        if (id == -1) {
            throw new ServletException("Could not parse id parameter");
        }
        return id;
    }
}
