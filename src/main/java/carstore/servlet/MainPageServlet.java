package carstore.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet showing main page.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("")
public class MainPageServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(MainPageServlet.class);

    /**
     * Displays webapp-root page.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/view/" + "showAllCars.jsp").forward(req, resp);

    }
}
