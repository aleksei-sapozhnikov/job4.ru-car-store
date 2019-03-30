package carstore.factory;

import carstore.constants.Attributes;
import carstore.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
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
     *
     */
    public Set<Image> createImageSet(HttpServletRequest req) throws IOException, ServletException {
        var result = new LinkedHashSet<Image>();
        var imgParts = req.getParts().stream()
                .filter(part -> part.getName().startsWith(Attributes.PRM_IMAGE_KEY_START.v()))
                .collect(Collectors.toList());
        for (var part : imgParts) {
            var data = this.readByteArray(part);
            result.add(Image.of(data));
        }
        return result;
    }

    private byte[] readByteArray(Part part) throws IOException {
        byte[] result;
        try (var in = part.getInputStream();
             var out = new ByteArrayOutputStream()) {
            Utils.readFullInput(in, out);
            result = out.toByteArray();
        }
        return result;
    }
}
