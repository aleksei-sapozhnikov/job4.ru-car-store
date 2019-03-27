//package carstore.servlet;
//
//import carstore.model.Image;
//import carstore.store.NewImageStore;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.Session;
//import util.Utils;
//
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
///**
// * Returns image by its id.
// *
// * @author Aleksei Sapozhnikov (vermucht@gmail.com)
// * @version 0.1
// * @since 0.1
// */
//@WebServlet(value = "/image", loadOnStartup = 0)
//@MultipartConfig(
//        maxFileSize = 1024 * 1024 * 5   // 5 MB
//)
//public class GetImageServlet extends HttpServlet {
//    /**
//     * Logger.
//     */
//    @SuppressWarnings("unused")
//    private static final Logger LOG = LogManager.getLogger(GetImageServlet.class);
//    /**
//     * Image returned in case if asked image was not found.
//     */
//    private static final byte[] NOT_FOUND_IMAGE = GetImageServlet.readResource("carstore/store/image-not-found.png");
//
//    /**
//     * Reads and returns resource as byte array,
//     *
//     * @param path Resource path.
//     * @return Resource as byte array.
//     */
//    private static byte[] readResource(String path) {
//        byte[] result = new byte[0];
//        try (var in = GetImageServlet.class.getClassLoader().getResourceAsStream(path);
//             var out = new ByteArrayOutputStream()) {
//            if (in == null) {
//                throw new RuntimeException("Input resource is null");
//            }
//            Utils.readFullInput(in, out);
//            result = out.toByteArray();
//        } catch (Exception e) {
//            LOG.fatal(e.getMessage(), e);
//        }
//        return result;
//    }
//
//    /**
//     * Initializes fields.
//     */
//    @Override
//    public void init() {
//        var context = this.getServletContext();
//    }
//
//    /**
//     * Returns image asked by id.
//     *
//     * @param req  Request object.
//     * @param resp Response object.
//     * @throws IOException In case of problems.
//     */
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        var imageStore = new NewImageStore((Session) req.getAttribute("hibernateSession"));
//        long id = Utils.parseLong(req.getParameter("id"), -1);
//        var image = imageStore.get(id);
//        this.writeToResponse(resp, image);
//    }
//
//    /**
//     * Writes image to response if image != null
//     * or writes 'not found' image if image == null.
//     *
//     * @param resp  Response object.
//     * @param image Image found.
//     * @throws IOException In case of problems.
//     */
//    private void writeToResponse(HttpServletResponse resp, Image image) throws IOException {
//        try (var out = resp.getOutputStream()) {
//            if (image != null) {
//                out.write(image.getData());
//            } else {
//                out.write(NOT_FOUND_IMAGE);
//            }
//        }
//    }
//}
