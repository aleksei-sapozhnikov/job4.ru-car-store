package carstore.servlet;

import carstore.constants.ServletContextAttributes;
import carstore.model.Item;
import carstore.model.car.Car;
import carstore.model.car.Image;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Servlet to work with cars.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/cars")
public class CarServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(CarServlet.class);
    private final Gson gson = new Gson();
    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        var context = this.getServletContext();
        this.factory = (SessionFactory)
                context.getAttribute(ServletContextAttributes.SESSION_FACTORY.v());
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
                var json = this.gson.toJson(items);
                this.gson.toJson(items, writer);
            }
        }
    }

    private Item carToItem(Car car) {
        var item = new Item();
        //
        item.setTitle(String.format("%s %s",
                car.getMark().getManufacturer(),
                car.getMark().getModel()));
        //
        item.setPrice(car.getPrice());
        //
        Map<String, String> descriptions = new HashMap<>();
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
        item.setDescriptions(descriptions);
        //
        var images = car.getImages().stream()
                .map(Image::getId)
                .collect(Collectors.toList());
        item.setImagesIds(images);
        return item;
    }

    /**
     * Performs transaction: creates session, performs given operations, commits and closes.
     *
     * @param operations Function: operations to perform.
     * @param <T>        Result type.
     * @return Operation result.
     */
    private <T> T performTransaction(final Function<Session, T> operations) {
        T result;
        try (final var session = this.factory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                result = operations.apply(session);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
        return result;
    }

}
