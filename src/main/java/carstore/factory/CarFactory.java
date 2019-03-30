package carstore.factory;

import carstore.constants.Attributes;
import carstore.model.Car;
import carstore.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates car object from different parameters.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class CarFactory {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CarFactory.class);

    public Car createCar(HttpServletRequest req, User owner) throws IOException, ServletException {
        var strParams = this.getStrParams(req);
        var intParams = this.getIntParams(req);
        long carId = getCarId(req);
        var car = Car.of(owner, strParams, intParams);
        car.setId(carId);
        return car;
    }

    private long getCarId(HttpServletRequest req) {
        var carIdStr = req.getParameter(Attributes.PRM_CAR_ID.v());
        return Utils.parseLong(carIdStr, 0);
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


}
