package carstore.factory;

import carstore.model.Car;
import carstore.model.FrontItem;
import carstore.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Factory producing FrontItem objects.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class FrontItemFactory {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(FrontItemFactory.class);

    /**
     * Creates new FrontItem object from given parameters.
     *
     * @param car    Car object.
     * @param images Images for this cae.
     * @return Item object from given parameters.
     */
    public FrontItem newFrontItem(Car car, List<Image> images) {
        var imageSet = new LinkedHashSet<>(images);
        return FrontItem.of(car, imageSet);
    }
}
