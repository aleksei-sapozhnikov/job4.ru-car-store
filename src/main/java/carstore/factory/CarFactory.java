package carstore.factory;

import carstore.constants.Attributes;
import carstore.exception.InvalidParametersException;
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
    /**
     * Validator for parameters which are used to create car.
     */
    private CarParamsValidator validator;

    /**
     * Constructor.
     *
     * @param validator Parameters validator.
     */
    public CarFactory(CarParamsValidator validator) {
        this.validator = validator;
    }

    /**
     * Creates new car from request with parameters given by "parts".
     *
     * @param req   Request object.
     * @param owner User to assign as car owner.
     * @return Car object created.
     * @throws IOException                In case of problems.
     * @throws ServletException           In case of problems.
     * @throws InvalidParametersException If given parameters do not match the validator.
     */
    public Car createCar(HttpServletRequest req, User owner) throws IOException, ServletException, InvalidParametersException {
        var strParams = this.getStrParams(req);
        var intParams = this.getIntParams(req);
        if (!this.validator.areValidParams(strParams, intParams)) {
            throw new InvalidParametersException("One of car parameters did not pass validation.");
        }
        var car = Car.of(owner, strParams, intParams);
        car.setAvailable(this.getIsAvailable(req));
        car.setId(this.getCarId(req));
        return car;
    }

    /**
     * Returns car id from request.
     *
     * @param req Request object.
     * @return Car id found or 0 if could not read id.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
    private long getCarId(HttpServletRequest req) throws IOException, ServletException {
        var part = req.getPart(Attributes.PRM_CAR_ID.v());
        return part == null ? 0 : Utils.readLong(part);
    }

    /**
     * Returns if car is available for buy or not.
     *
     * @param req Request object.
     * @return Car available for buy status.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
    private boolean getIsAvailable(HttpServletRequest req) throws IOException, ServletException {
        var part = req.getPart(Attributes.PRM_CAR_AVAILABLE.v());
        var str = Utils.readString(part);
        return str.equals("true");
    }

    /**
     * Returns map of Car's string parameters.
     *
     * @param req Request object.
     * @return Map of Car's string parameters.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
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

    /**
     * Returns map of Car's integer parameters.
     *
     * @param req Request object.
     * @return Map of Car's integer parameters.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
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

    /**
     * Reads and puts string parameter under given key to parameters map.
     *
     * @param map     Parameters map.
     * @param mapKey  Key to put parameter under.
     * @param req     Request object to read value from.
     * @param partKey Key of request part containing the value.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
    private void putStringParameter(Map<Car.StrParam, String> map, Car.StrParam mapKey,
                                    HttpServletRequest req, String partKey
    ) throws IOException, ServletException {
        var part = req.getPart(partKey);
        if (part != null) {
            map.put(mapKey, Utils.readString(part));
        }
    }

    /**
     * Reads and puts integer parameter under given key to parameters map.
     *
     * @param map     Parameters map.
     * @param mapKey  Key to put parameter under.
     * @param req     Request object to read value from.
     * @param partKey Key of request part containing the value.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
    private void putIntegerParameter(Map<Car.IntParam, Integer> map, Car.IntParam mapKey,
                                     HttpServletRequest req, String partKey
    ) throws IOException, ServletException {
        var part = req.getPart(partKey);
        if (part != null) {
            map.put(mapKey, Utils.readInteger(part));
        }
    }


}
