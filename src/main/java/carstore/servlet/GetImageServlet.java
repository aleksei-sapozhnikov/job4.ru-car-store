package carstore.servlet;

import carstore.constants.ServletContextAttributes;
import carstore.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns image by its id.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/image")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5   // 5 MB
)
public class GetImageServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(GetImageServlet.class);

    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        var context = this.getServletContext();
        this.factory = (SessionFactory)
                context.getAttribute(ServletContextAttributes.SESSION_FACTORY.v());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var idParam = req.getParameter("id");
        var id = Long.parseLong(idParam);
        try (var session = this.factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                var image = session.get(Image.class, id);
                try (var out = resp.getOutputStream()) {
                    out.write(image.getData());
                }
                tx.rollback();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
