package carstore.factory;

import carstore.model.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;

/**
 * Validator for parameters used to create Car instances.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class CarParamsValidator {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CarParamsValidator.class);
    public static final String PTN_MANUFACTURER = "[a-zA-Z][a-zA-Z0-9\\s-/]{2,254}";
    public static final String PTN_MODEL = "[a-zA-Z0-9][a-zA-Z0-9\\s-/]{2,254}";
    public static final String PTN_COLOR = "[a-zA-Z][a-zA-Z\\s-/]{2,254}";
    public static final String PTN_BODY_TYPE = "[a-zA-Z]{3,255}";
    public static final String PTN_NEWNESS = "(new|used|old)";
    public static final String PTN_ENGINE_FUEL = "(gasoline|kerosene|gas|electricity)";
    public static final String PTN_TRANSMISSION_TYPE = "(automatic|cvt|manual)";

    public boolean areValidParams(Map<Car.StrParam, String> strParams, Map<Car.IntParam, Integer> intParams) {
        var result = strParams.get(Car.StrParam.MANUFACTURER).matches(PTN_MANUFACTURER);
        result = result && strParams.get(Car.StrParam.MODEL).matches(PTN_MODEL);
        result = result && strParams.get(Car.StrParam.COLOR).matches(PTN_COLOR);
        result = result && strParams.get(Car.StrParam.BODY_TYPE).matches(PTN_BODY_TYPE);
        result = result && strParams.get(Car.StrParam.NEWNESS).matches(PTN_NEWNESS);
        result = result && strParams.get(Car.StrParam.ENGINE_FUEL).matches(PTN_ENGINE_FUEL);
        result = result && strParams.get(Car.StrParam.TRANSMISSION_TYPE).matches(PTN_TRANSMISSION_TYPE);
        result = result && intParams.get(Car.IntParam.PRICE) > 0;
        result = result
                && intParams.get(Car.IntParam.YEAR_MANUFACTURED) > 1900
                && intParams.get(Car.IntParam.YEAR_MANUFACTURED) < LocalDate.now().getYear();
        result = result && intParams.get(Car.IntParam.MILEAGE) >= 0;
        result = result && intParams.get(Car.IntParam.ENGINE_VOLUME) > 0;
        return result;
    }
}
