package carstore.model;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Frontend item object to show on main page.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class FrontItem {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(FrontItem.class);

    private String title;

    private String price;

    private String seller;

    private String contacts;

    private Map<String, String> descriptions = new HashMap<>();

    public static FrontItem of(Car car) {
        var item = new FrontItem();
        item.title = car.getManufacturer() + " " + car.getModel();
        item.price = String.valueOf(car.getPrice());
        item.seller = car.getOwner().getLogin();
        item.contacts = car.getOwner().getPhone();
        item.descriptions.put("Body", String.join("; ",
                car.getBodyType(),
                car.getColor()));
        item.descriptions.put("Age", String.join("; ",
                car.getNewness(),
                car.getYearManufactured() + " y.m.",
                String.format(new Locale("ru"), "%,d", car.getMileage()) + " km"));
        return item;
    }

    public static void main(String[] args) {
        var car = new Car()
                .setManufacturer("Ford").setModel("Transit FX-300")
                .setPrice(1500)
                .setOwner(User.of("Vasily", "12345", "564-345-23"))
                .setBodyType("Sedan").setColor("black")
                .setNewness("used").setYearManufactured(2010).setMileage(150_000);
        var item = FrontItem.of(car);
        var gson = new Gson().toJson(item);
        System.out.println(gson);
    }
}
