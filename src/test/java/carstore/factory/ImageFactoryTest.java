package carstore.factory;

import carstore.constants.Attributes;
import carstore.model.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({Image.class, Utils.class})
public class ImageFactoryTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private Part imgPart1, imgPart2, imgPart3, otherPart1, otherPart2;
    @Mock
    private Image image1, image2, image3;

    @Before
    public void initMocks() throws IOException, ServletException {
        MockitoAnnotations.initMocks(this);
        when(this.req.getParts()).thenReturn(List.of(this.imgPart1, this.otherPart1, this.imgPart2, this.imgPart3, this.otherPart2));
        when(this.imgPart1.getName()).thenReturn(String.format("%sOne", Attributes.PRM_IMAGE_KEY_START.v()));
        when(this.imgPart2.getName()).thenReturn(String.format("%sTwo", Attributes.PRM_IMAGE_KEY_START.v()));
        when(this.imgPart3.getName()).thenReturn(String.format("%sThree", Attributes.PRM_IMAGE_KEY_START.v()));
        when(this.otherPart1.getName()).thenReturn("otherOne");
        when(this.otherPart2.getName()).thenReturn("otherTwo");
        // mock data read
        PowerMockito.mockStatic(Utils.class);
        when(Utils.readByteArray(this.imgPart1)).thenReturn("img1".getBytes());
        when(Utils.readByteArray(this.imgPart2)).thenReturn("img2".getBytes());
        when(Utils.readByteArray(this.imgPart3)).thenReturn("img3".getBytes());
        when(Utils.readByteArray(this.otherPart1)).thenReturn("other1".getBytes());
        when(Utils.readByteArray(this.otherPart2)).thenReturn("other".getBytes());
        // mock image creation result
        PowerMockito.mockStatic(Image.class);
        when(Image.of("img1".getBytes())).thenReturn(this.image1);
        when(Image.of("img2".getBytes())).thenReturn(this.image2);
        when(Image.of("img3".getBytes())).thenReturn(this.image3);
    }

    @Test
    public void whenRequestThenSetOfImagesFromImageParts() throws IOException, ServletException {
        var factory = new ImageFactory();
        var result = factory.createImageSet(this.req);
        assertEquals(result, Set.of(
                this.image1,
                this.image2,
                this.image3
        ));
    }
}