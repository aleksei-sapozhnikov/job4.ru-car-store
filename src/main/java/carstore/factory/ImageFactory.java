package carstore.factory;

import carstore.constants.Attributes;
import carstore.model.Image;
import carstore.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Creates Image objects from different parameters.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class ImageFactory {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(ImageFactory.class);

    /**
     * Creates set of images from request parameters.
     *
     * @param req Request object.
     * @return Set of Image objects.
     * @throws IOException      In case of problems.
     * @throws ServletException In case of problems.
     */
    public Set<Image> createImageSet(HttpServletRequest req) throws IOException, ServletException {
        var result = new LinkedHashSet<Image>();
        var imgParts = req.getParts().stream()
                .filter(part -> part.getName().startsWith(Attributes.PRM_IMAGE_KEY_START.v()))
                .collect(Collectors.toList());
        for (var part : imgParts) {
            var data = Utils.readByteArray(part);
            if (data.length != 0) {
                result.add(Image.of(data));
            }
        }
        return result;
    }
}
