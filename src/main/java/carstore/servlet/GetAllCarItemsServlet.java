package carstore.servlet;

import carstore.constants.Attributes;
import carstore.factory.FrontItemFactory;
import carstore.model.Car;
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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
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
     * Dispatch of methods to get list of cars in case of different filters.
     */
    private final Map<String, BiFunction<String, Session, List<Car>>> getCarMethodsDispatch = new HashMap<>();

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
        this.initDispatch();
    }

    private void initDispatch() {
        this.getCarMethodsDispatch.put(Attributes.PRM_FILTER_BY_CREATED_TODAY.v(), this::getCreatedToday);
        this.getCarMethodsDispatch.put(Attributes.PRM_FILTER_BY_WITH_IMAGE.v(), this::getWithImage);
        this.getCarMethodsDispatch.put(Attributes.PRM_FILTER_BY_MANUFACTURER.v(), this::getByManufacturer);
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
        var filterBy = req.getParameter(Attributes.PRM_FILTER_BY.v());
        var filterValue = req.getParameter(Attributes.PRM_FILTER_VALUE.v());
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var cars = this.getCarMethodsDispatch
                .getOrDefault(filterBy, this::getAllCars)
                .apply(filterValue, hbSession);
        var items = cars.stream().map(car -> {
            var images = this.imageStore.getForCar(car.getId()).apply(hbSession);
            return this.itemFactory.newFrontItem(car, images);
        }).collect(Collectors.toCollection(LinkedHashSet::new));
        try (var writer = resp.getWriter()) {
            this.jsonParser.toJson(items, writer);
        }
    }

    /**
     * Addresses to storage to get cars created today.
     *
     * @param searchValue Search value parameter (not used).
     * @param session     Hibernate session to use.
     * @return List of cars created today.
     */
    private List<Car> getCreatedToday(String searchValue, Session session) {
        return this.carStore.getAllCreatedToday().apply(session);
    }

    /**
     * Addresses to storage to get cars with image.
     *
     * @param searchValue Search value parameter (not used).
     * @param session     Hibernate session to use.
     * @return List of cars with image.
     */
    private List<Car> getWithImage(String searchValue, Session session) {
        return this.carStore.getAllWithImage().apply(session);
    }

    /**
     * Addresses to storage to get cars with given manufacturer.
     *
     * @param manufacturer Manufacturer to search for.
     * @param session      Hibernate session to use.
     * @return List of cars with given manufacturer. Empty list if no such cars found.
     */
    private List<Car> getByManufacturer(String manufacturer, Session session) {
        return this.carStore.getByManufacturer(manufacturer).apply(session);
    }

    /**
     * Addresses to storage to get all cars.
     *
     * @param searchValue Search value parameter (not used).
     * @param session     Hibernate session to use.
     * @return List of all cars in storage.
     */
    private List<Car> getAllCars(String searchValue, Session session) {
        return this.carStore.getAll().apply(session);
    }
}
