package carstore.factory;

import carstore.model.Car;
import carstore.model.FrontItem;
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

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest(FrontItem.class)
public class FrontItemFactoryTest {

    @Mock
    FrontItem item;
    @Mock
    Car car;
    @Mock
    Image image1, image2, image3;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCallNewFrontItemThenItemCreated() {
        PowerMockito.mockStatic(FrontItem.class);
        when(FrontItem.of(this.car, Set.of(this.image1, this.image2, this.image3))).thenReturn(this.item);
        var factory = new FrontItemFactory();
        assertSame(this.item, factory.newFrontItem(this.car, List.of(this.image1, this.image2, this.image3)));
    }
}