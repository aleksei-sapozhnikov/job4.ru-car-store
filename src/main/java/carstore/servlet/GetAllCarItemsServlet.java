package carstore.servlet;

import carstore.constants.ConstContext;
import carstore.model.Image;
import carstore.model.Item;
import carstore.model.car.Car;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servlet to work with cars.
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
    private final Gson gson = new Gson();
    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        var context = this.getServletContext();
        this.factory = (SessionFactory)
                context.getAttribute(ConstContext.SESSION_FACTORY.v());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var session = this.factory.openSession()) {
            @SuppressWarnings("unchecked")
            List<Car> cars = session.createQuery("from Car").list();
            List<Item> items = cars.stream()
                    .map(this::carToItem)
                    .collect(Collectors.toList());
            try (var writer = resp.getWriter()) {
                this.gson.toJson(items, writer);
            }
        }
    }

    private Item carToItem(Car car) {
        var item = new Item();
        item.setTitle(String.format("%s %s",
                car.getMark().getManufacturer(),
                car.getMark().getModel()));
        item.setStoreId(car.getId());
        item.setPrice(car.getPrice());
        item.setDescriptions(this.carToDescriptionsForItem(car));
        item.setImagesIds(this.carToImagesForItem(car));
        return item;
    }

    private List<Long> carToImagesForItem(Car car) {
        return car.getImages().stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }

    private Map<String, String> carToDescriptionsForItem(Car car) {
        Map<String, String> descriptions = new LinkedHashMap<>();
        descriptions.put("Seller", String.join("; ",
                car.getSeller().getLogin(),
                car.getSeller().getPhone()));
        descriptions.put("Age", String.join("; ",
                car.getAge().getNewness(),
                String.valueOf(car.getAge().getManufactureYear()),
                String.valueOf(car.getAge().getMileage())));
        descriptions.put("Body", String.join("; ",
                car.getBody().getType(),
                car.getBody().getColor()));
        descriptions.put("Engine", String.join("; ",
                car.getEngine().getEngineType(),
                String.valueOf(car.getEngine().getEngineVolume())));
        descriptions.put("Chassis", String.join("; ",
                car.getChassis().getTransmissionType()));
        return descriptions;
    }

}
