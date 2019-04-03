package carstore.servlet;

import carstore.constants.Attributes;
import carstore.factory.FrontItemFactory;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

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
    private Gson jsonParser;
    /**
     * Car store.
     */
    private CarStore carStore;
    /**
     * Image store.
     */
    private ImageStore imageStore;
    /**
     * FrontItem factory.
     */
    private FrontItemFactory itemFactory;

    /**
     * Initializes fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.jsonParser = (Gson) ctx.getAttribute(Attributes.ATR_JSON_PARSER.v());
        this.carStore = (CarStore) ctx.getAttribute(Attributes.ATR_CAR_STORE.v());
        this.imageStore = (ImageStore) ctx.getAttribute(Attributes.ATR_IMAGE_STORE.v());
        this.itemFactory = (FrontItemFactory) ctx.getAttribute(Attributes.ATR_ITEM_FACTORY.v());
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
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var cars = this.carStore.getAll().apply(hbSession);
        var items = cars.stream().map(car -> {
            var imgListFunc = this.imageStore.getForCar(car.getId());
            var images = imgListFunc.apply(hbSession);
            return this.itemFactory.newFrontItem(car, images);
        }).collect(Collectors.toCollection(LinkedHashSet::new));
        try (var writer = resp.getWriter()) {
            this.jsonParser.toJson(items, writer);
        }
    }
}
