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

    public boolean areValidParams(Map<Car.StrParam, String> strParams, Map<Car.IntParam, Integer> intParams) {
        var result = strParams.get(Car.StrParam.MANUFACTURER).matches("[a-zA-Z][a-zA-Z0-9\\s-/]{2,254}");
        result = result && strParams.get(Car.StrParam.MODEL).matches("[a-zA-Z][a-zA-Z0-9\\s-/]{2,254}");
        result = result && strParams.get(Car.StrParam.COLOR).matches("[a-zA-Z][a-zA-Z\\s-/]{2,254}");
        result = result && strParams.get(Car.StrParam.BODY_TYPE).matches("[a-zA-Z]{3,255}");
        result = result && strParams.get(Car.StrParam.NEWNESS).matches("(new|used|old)");
        result = result && strParams.get(Car.StrParam.ENGINE_FUEL).matches("(gasoline|kerosene|gas|electricity)");
        result = result && strParams.get(Car.StrParam.TRANSMISSION_TYPE).matches("(automatic|cvt|manual)");
        result = result && intParams.get(Car.IntParam.PRICE) > 0;
        result = result
                && intParams.get(Car.IntParam.YEAR_MANUFACTURED) > 1900
                && intParams.get(Car.IntParam.YEAR_MANUFACTURED) < LocalDate.now().getYear();
        result = result && intParams.get(Car.IntParam.MILEAGE) >= 0;
        result = result && intParams.get(Car.IntParam.ENGINE_VOLUME) > 0;
        return result;
    }
}
