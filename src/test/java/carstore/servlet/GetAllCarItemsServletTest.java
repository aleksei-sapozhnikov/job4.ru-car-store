package carstore.servlet;

import carstore.constants.Attributes;
import carstore.factory.FrontItemFactory;
import carstore.model.Car;
import carstore.model.FrontItem;
import carstore.model.Image;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest(Gson.class)
public class GetAllCarItemsServletTest {

    @Mock
    private Gson jsonParser;
    @Mock
    private ServletContext sContext;
    @Mock
    private ServletConfig sConfig;
    @Mock
    private Session hbSession;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private PrintWriter writer;
    @Mock
    private CarStore carStore;
    @Mock
    private ImageStore imageStore;
    @Mock
    private FrontItemFactory itemFactory;
    @Mock
    private Car carId5, carId7;
    @Mock
    private List<Image> imageList;
    @Mock
    private FrontItem itemCarId5, itemCarId7;
    @Mock
    private Function<Session, List<Car>> getCarsFunction;
    @Mock
    private Function<Session, List<Image>> getImagesFunction;

    @Before
    public void initMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(this.sConfig.getServletContext()).thenReturn(this.sContext);
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.hbSession);
        when(this.sContext.getAttribute(Attributes.ATR_CAR_STORE.v())).thenReturn(this.carStore);
        when(this.sContext.getAttribute(Attributes.ATR_IMAGE_STORE.v())).thenReturn(this.imageStore);
        when(this.sContext.getAttribute(Attributes.ATR_ITEM_FACTORY.v())).thenReturn(this.itemFactory);
        when(this.sContext.getAttribute(Attributes.ATR_JSON_PARSER.v())).thenReturn(this.jsonParser);
        when(this.resp.getWriter()).thenReturn(this.writer);
        // setup model objects
        when(this.carId5.getId()).thenReturn(5L);
        when(this.carId7.getId()).thenReturn(7L);
        // setup cars returned from storage
        when(this.getCarsFunction.apply(this.hbSession))
                .thenReturn(List.of(this.carId5, this.carId7));
        // setup get images for cars
        when(this.imageStore.getForCar(anyLong())).thenReturn(this.getImagesFunction);
        when(this.getImagesFunction.apply(this.hbSession)).thenReturn(this.imageList);
        // setup get items for cars
        when(this.itemFactory.newFrontItem(this.carId5, this.imageList)).thenReturn(this.itemCarId5);
        when(this.itemFactory.newFrontItem(this.carId7, this.imageList)).thenReturn(this.itemCarId7);
    }

    @Test
    public void whenDoGetWithoutParametersThenGetAllCarsAndConvertToItems() throws ServletException, IOException {
        when(this.carStore.getAll()).thenReturn(this.getCarsFunction);
        // actions
        var servlet = new GetAllCarItemsServlet();
        servlet.init(this.sConfig);
        servlet.doGet(this.req, this.resp);
        // verify
        verify(this.jsonParser).toJson(Set.of(this.itemCarId5, itemCarId7), this.writer);
    }

    @Test
    public void whenDoGetFilterByCreatedTodayThenGetAllCreatedToday() throws ServletException, IOException {
        when(this.req.getParameter(Attributes.PRM_FILTER_BY.v())).thenReturn(Attributes.PRM_FILTER_BY_CREATED_TODAY.v());
        when(this.carStore.getAllCreatedToday()).thenReturn(this.getCarsFunction);
        // do actions
        var servlet = new GetAllCarItemsServlet();
        servlet.init(this.sConfig);
        servlet.doGet(this.req, this.resp);
        // verify
        verify(this.jsonParser).toJson(Set.of(this.itemCarId5, itemCarId7), this.writer);
    }

    @Test
    public void whenDoGetFilterByWithImageThenGetAllWithImage() throws ServletException, IOException {
        when(this.req.getParameter(Attributes.PRM_FILTER_BY.v())).thenReturn(Attributes.PRM_FILTER_BY_WITH_IMAGE.v());
        when(this.carStore.getAllWithImage()).thenReturn(this.getCarsFunction);
        // do actions
        var servlet = new GetAllCarItemsServlet();
        servlet.init(this.sConfig);
        servlet.doGet(this.req, this.resp);
        // verify
        verify(this.jsonParser).toJson(Set.of(this.itemCarId5, itemCarId7), this.writer);
    }

    @Test
    public void whenDoGetFilterByManufacturerWithImageThenGetAllWithImage() throws ServletException, IOException {
        when(this.req.getParameter(Attributes.PRM_FILTER_BY.v())).thenReturn(Attributes.PRM_FILTER_BY_MANUFACTURER.v());
        when(this.req.getParameter(Attributes.PRM_FILTER_VALUE.v())).thenReturn("Toyota");
        when(this.carStore.getByManufacturer("Toyota")).thenReturn(this.getCarsFunction);
        // do actions
        var servlet = new GetAllCarItemsServlet();
        servlet.init(this.sConfig);
        servlet.doGet(this.req, this.resp);
        // verify
        verify(this.jsonParser).toJson(Set.of(this.itemCarId5, itemCarId7), this.writer);
    }
}