package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.Car;
import carstore.model.Image;
import carstore.model.User;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import carstore.store.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controls adding car.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/addCar")
@MultipartConfig
public class AddCarServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AddCarServlet.class);
    /**
     * Car store.
     */
    private CarStore carStore;
    /**
     * User store.
     */
    private UserStore userStore;
    /**
     * Image store.
     */
    private ImageStore imageStore;

    /**
     * Initiates fields.
     */
    @Override
    public void init() {
        var ctx = this.getServletContext();
        this.carStore = (CarStore) ctx.getAttribute(Attributes.ATR_CAR_STORE.v());
        this.userStore = (UserStore) ctx.getAttribute(Attributes.ATR_USER_STORE.v());
        this.imageStore = (ImageStore) ctx.getAttribute(Attributes.ATR_IMAGE_STORE.v());
    }

    /**
     * Shows form to add car.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws ServletException In case of problems.
     * @throws IOException      In case of problems.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(
                String.join("/", WebApp.VIEW_ROOT.v(), WebApp.PG_CREATE_CAR.v())
        ).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = this.createCar(req);
        var images = this.getImages(req);
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        this.saveOrUpdate(hbSession, images, car);
        var resultMsg = String.format("Car (%s %s) saved.", car.getManufacturer(), car.getModel());
        var redirectPath = new StringBuilder()
//                .append(WebApp.BASEDIR.v())
                .append(req.getContextPath())
                .append("?")
                .append(WebApp.MSG_SUCCESS.v()).append("=").append(resultMsg)
                .toString();
        resp.sendRedirect(redirectPath);
    }


    private User getLoggedUser(HttpServletRequest req) {
        var hbSession = (Session) req.getAttribute(Attributes.ATR_HB_SESSION.v());
        var httpSession = req.getSession(false);
        var userId = (Long) httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        return this.userStore.get(userId).apply(hbSession);
    }

    private Car createCar(HttpServletRequest req) throws IOException, ServletException {
        var user = this.getLoggedUser(req);
        var strParams = this.getStrParams(req);
        var intParams = this.getIntParams(req);
        var carId = Utils.parseLong(req.getParameter(Attributes.PRM_CAR_ID.v()), 0);
        var car = Car.of(user, strParams, intParams);
        car.setId(carId);
        return car;
    }

    private void saveOrUpdate(Session hbSession, Set<Image> images, Car car) {
        hbSession.saveOrUpdate(car);
        images.forEach(img -> {
            img.setCar(car);
            hbSession.saveOrUpdate(img);
        });
    }

    private Map<Car.StrParam, String> getStrParams(HttpServletRequest req) throws IOException, ServletException {
        var result = new HashMap<Car.StrParam, String>();
        this.putStringParameter(result, Car.StrParam.MANUFACTURER, req, Attributes.PRM_CAR_MANUFACTURER.v());
        this.putStringParameter(result, Car.StrParam.MODEL, req, Attributes.PRM_CAR_MODEL.v());
        this.putStringParameter(result, Car.StrParam.NEWNESS, req, Attributes.PRM_CAR_NEWNESS.v());
        this.putStringParameter(result, Car.StrParam.BODY_TYPE, req, Attributes.PRM_CAR_BODY_TYPE.v());
        this.putStringParameter(result, Car.StrParam.COLOR, req, Attributes.PRM_CAR_COLOR.v());
        this.putStringParameter(result, Car.StrParam.ENGINE_FUEL, req, Attributes.PRM_CAR_ENGINE_FUEL.v());
        this.putStringParameter(result, Car.StrParam.TRANSMISSION_TYPE, req, Attributes.PRM_CAR_TRANSMISSION_TYPE.v());
        if (result.size() != Car.StrParam.values().length) {
            throw new ServletException("Not all car String parameters found.");
        }
        return result;
    }

    private Map<Car.IntParam, Integer> getIntParams(HttpServletRequest req) throws IOException, ServletException {
        var result = new HashMap<Car.IntParam, Integer>();
        this.putIntegerParameter(result, Car.IntParam.PRICE, req, Attributes.PRM_CAR_PRICE.v());
        this.putIntegerParameter(result, Car.IntParam.YEAR_MANUFACTURED, req, Attributes.PRM_CAR_YEAR_MANUFACTURED.v());
        this.putIntegerParameter(result, Car.IntParam.MILEAGE, req, Attributes.PRM_CAR_MILEAGE.v());
        this.putIntegerParameter(result, Car.IntParam.ENGINE_VOLUME, req, Attributes.PRM_CAR_ENGINE_VOLUME.v());
        if (result.size() != Car.IntParam.values().length) {
            throw new ServletException("Not all car Integer parameters found.");
        }
        return result;
    }

    private Set<Image> getImages(HttpServletRequest req) throws IOException, ServletException {
        var result = new LinkedHashSet<Image>();
        var imgParts = req.getParts().stream()
                .filter(part -> part.getName().startsWith(Attributes.PRM_IMAGE_KEY_START.v()))
                .collect(Collectors.toList());
        for (var part : imgParts) {
            var data = this.readByteArray(part);
            result.add(Image.of(data));
        }
        return result;
    }

    private void putStringParameter(Map<Car.StrParam, String> map, Car.StrParam mapKey,
                                    HttpServletRequest req, String partKey)
            throws IOException, ServletException {
        map.put(mapKey, this.readString(req.getPart(partKey)));
    }

    private String readString(Part part) throws IOException {
        String result;
        try (var in = part.getInputStream();
             var out = new ByteArrayOutputStream()) {
            Utils.readFullInput(in, out);
            result = new String(out.toByteArray(), StandardCharsets.UTF_8);
//            result = out.toString();
        }
        return result;
    }

    private void putIntegerParameter(Map<Car.IntParam, Integer> map, Car.IntParam mapKey,
                                     HttpServletRequest req, String partKey)
            throws IOException, ServletException {
        map.put(mapKey, this.readInteger(req.getPart(partKey)));
    }

    private Integer readInteger(Part part) throws IOException, ServletException {
        String result;
        try (var in = part.getInputStream();
             var out = new ByteArrayOutputStream()) {
            Utils.readFullInput(in, out);
            result = out.toString();
        }
        if (!(result.matches("\\d+"))) {
            throw new ServletException(String.format(
                    "Given parameter (%s) cannot be parsed as Integer value", result));
        }
        return Integer.valueOf(result);
    }

    private byte[] readByteArray(Part part) throws IOException {
        byte[] result;
        try (var in = part.getInputStream();
             var out = new ByteArrayOutputStream()) {
            Utils.readFullInput(in, out);
            result = out.toByteArray();
        }
        return result;
    }

}
