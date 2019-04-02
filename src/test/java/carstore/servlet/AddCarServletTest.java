package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.factory.CarFactory;
import carstore.factory.ImageFactory;
import carstore.model.Car;
import carstore.model.Image;
import carstore.model.User;
import carstore.store.CarStore;
import carstore.store.UserStore;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AddCarServletTest {

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
    private RequestDispatcher rDispatcher;
    @Mock
    private HttpSession httpSession;
    @Mock
    private CarStore carStore;
    @Mock
    private UserStore userStore;
    @Mock
    private CarFactory carFactory;
    @Mock
    private ImageFactory imageFactory;
    @Mock
    private User user;
    @Mock
    private Set<Image> imageSet;
    @Mock
    private Car car;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.sConfig.getServletContext()).thenReturn(this.sContext);
        when(this.sContext.getContextPath()).thenReturn("root");
        when(this.req.getContextPath()).thenReturn("root");
        when(this.req.getServletContext()).thenReturn(this.sContext);
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.hbSession);
        when(this.req.getSession(false)).thenReturn(this.httpSession);
        when(this.req.getRequestDispatcher(any(String.class))).thenReturn(this.rDispatcher);
        when(this.sContext.getAttribute(Attributes.ATR_CAR_STORE.v())).thenReturn(this.carStore);
        when(this.sContext.getAttribute(Attributes.ATR_USER_STORE.v())).thenReturn(this.userStore);
        when(this.sContext.getAttribute(Attributes.ATR_CAR_FACTORY.v())).thenReturn(this.carFactory);
        when(this.sContext.getAttribute(Attributes.ATR_IMAGE_FACTORY.v())).thenReturn(this.imageFactory);
    }

    @Test
    public void whenDestroyNothingHappens() {
        var servlet = new AddCarServlet();
        servlet.destroy();
        verifyZeroInteractions(
                sContext, sConfig, hbSession, req, resp, rDispatcher,
                httpSession, carStore, userStore, carFactory,
                imageFactory, user, imageSet, car
        );
    }

    @Test
    public void whenDoGetTheGoToCarFormPage() throws ServletException, IOException {
        var servlet = new AddCarServlet();
        servlet.doGet(req, resp);
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.req).getRequestDispatcher(pathCaptor.capture());
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.VIEW_ROOT.v()));
        assertTrue(path.contains(WebApp.PG_CREATE_CAR.v()));
        verify(this.rDispatcher).forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenDoPostThenCarSavedOrUpdatedAndRedirectMainPageWithSuccess() throws ServletException, IOException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn(111L);
        var getUserFunction = (Function<Session, User>) Mockito.mock(Function.class);
        when(this.userStore.get(111L)).thenReturn(getUserFunction);
        when(getUserFunction.apply(this.hbSession)).thenReturn(this.user);
        // validation will be ok
        when(this.user.getId()).thenReturn(55L);
        when(this.car.getOwner()).thenReturn(this.user);
        when(this.imageFactory.createImageSet(this.req)).thenReturn(this.imageSet);
        when(this.carFactory.createCar(this.req, this.user)).thenReturn(this.car);
        var saveUpdateCarConsumer = (Consumer<Session>) Mockito.mock(Consumer.class);
        when(this.carStore.saveOrUpdate(this.car, this.imageSet)).thenReturn(saveUpdateCarConsumer);
        when(this.car.getOwner()).thenReturn(this.user);    // validation ok: user is car owner
        when(this.user.getId()).thenReturn(111L);
        // actions
        var servlet = new AddCarServlet();
        servlet.init(this.sConfig);
        servlet.doPost(this.req, this.resp);
        // verify
        verify(saveUpdateCarConsumer).accept(this.hbSession);
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.resp).sendRedirect(pathCaptor.capture());
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.MSG_SUCCESS.v()));
    }

}