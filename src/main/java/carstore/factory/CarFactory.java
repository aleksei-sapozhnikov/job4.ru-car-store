package carstore.factory;

import carstore.constants.Attributes;
import carstore.model.Car;
import carstore.model.User;
import carstore.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    private long getCarId(HttpServletRequest req) throws IOException, ServletException {
        var part = req.getPart(Attributes.PRM_CAR_ID.v());
        return part == null ? 0 : Utils.readLong(part);
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
                                    HttpServletRequest req, String partKey
    ) throws IOException, ServletException {
        var part = req.getPart(partKey);
        if (part != null) {
            map.put(mapKey, Utils.readString(part));
        }
    }

    private void putIntegerParameter(Map<Car.IntParam, Integer> map, Car.IntParam mapKey,
                                     HttpServletRequest req, String partKey
    ) throws IOException, ServletException {
        var part = req.getPart(partKey);
        if (part != null) {
            map.put(mapKey, Utils.readInteger(part));
        }
    }


}
