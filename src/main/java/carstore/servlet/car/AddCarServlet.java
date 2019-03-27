//package carstore.servlet.car;
//
//import carstore.store.NewCarStore;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.Session;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Controls adding car.
// *
// * @author Aleksei Sapozhnikov (vermucht@gmail.com)
// * @version 0.1
// * @since 0.1
// */
//@WebServlet("/addCar")
//@MultipartConfig
//public class AddCarServlet extends AbstractCarServlet {
//    /**
//     * Logger.
//     */
//    @SuppressWarnings("unused")
//    private static final Logger LOG = LogManager.getLogger(AddCarServlet.class);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/view" + "/createCar.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        var carStore = new NewCarStore((Session) req.getAttribute("hibernateSession"));
//        var car = this.createCarFromParameters(req);
//        carStore.save(car);
//        resp.sendRedirect(this.getServletContext().getContextPath() + "?id=" + car.getId());
//    }
//}
