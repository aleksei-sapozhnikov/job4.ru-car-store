package carstore.servlet;

import carstore.constants.Attributes;
import carstore.model.Image;
import carstore.store.ImageStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import util.Utils;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Returns image by its id.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet(value = "/image", loadOnStartup = 0)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5   // 5 MB
)
public class GetImageServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(GetImageServlet.class);
    /**
     * Image returned in case if asked image was not found.
     */
    private static final byte[] NOT_FOUND_IMAGE =
            Utils.readResource(GetImageServlet.class, "carstore/store/image-not-found.png");
    /**
     * Image store.
     */
    private ImageStore imageStore;

    /**
     * Returns copy of default image which is sent
     * when asked image was not found.
     *
     * @return Data of "not found" image.
     */
    public static byte[] getNotFoundImage() {
        return Arrays.copyOf(NOT_FOUND_IMAGE, NOT_FOUND_IMAGE.length);
    }

    /**
     * Initializes fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.imageStore = (ImageStore) ctx.getAttribute(Attributes.ATR_IMAGE_STORE.v());
    }

    /**
     * Returns image asked by id.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws IOException In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var idStr = req.getParameter(Attributes.PRM_IMAGE_ID.v());
        var id = Utils.parseLong(idStr, -1);
        var image = this.imageStore.get(id).apply(hbSession);
        this.writeToResponse(resp, image);
    }

    /**
     * Writes image to response if image != null
     * or writes 'not found' image if image == null.
     *
     * @param resp  Response object.
     * @param image Image found.
     * @throws IOException In case of problems.
     */
    private void writeToResponse(HttpServletResponse resp, Image image) throws IOException {
        try (var out = resp.getOutputStream()) {
            if (image != null) {
                out.write(image.getData());
            } else {
                out.write(NOT_FOUND_IMAGE);
            }
        }
    }
}
