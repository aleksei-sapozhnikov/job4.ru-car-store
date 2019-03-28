package carstore.filter;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.Car;
import carstore.model.User;
import carstore.store.CarStore;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class UserCanEditCarFilterTest {

    @Mock
    private ServletContext sContext;
    @Mock
    private FilterConfig fConfig;
    @Mock
    private Session hbSession;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private FilterChain chain;
    @Mock
    private HttpSession httpSession;
    @Mock
    private Car car;
    @Mock
    private CarStore carStore;
    @Mock
    private User user;
    @Mock
    private Function<Session, Car> getCarFunction;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.fConfig.getServletContext()).thenReturn(this.sContext);
        when(this.sContext.getAttribute(Attributes.ATR_CAR_STORE.v())).thenReturn(this.carStore);
        when(this.req.getSession(false)).thenReturn(this.httpSession);
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.hbSession);
    }

    @Test
    public void justForCoverageDestroy() {
        var filter = new UserCanEditCarFilter();
        filter.destroy();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void whenLoggedIdEqualsCarOwnerIdThenDoFilter() throws IOException, ServletException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn(111L);
        when(this.req.getParameter(Attributes.PRM_CAR_STORE_ID.v())).thenReturn("222");
        when(this.carStore.get(222)).thenReturn(this.getCarFunction);
        when(this.getCarFunction.apply(this.hbSession)).thenReturn(this.car);
        when(this.car.getOwner()).thenReturn(this.user);
        when(this.user.getId()).thenReturn(111L);
        // initialize
        var filter = new UserCanEditCarFilter();
        filter.init(this.fConfig);
        verify(this.fConfig).getServletContext();
        verify(this.sContext).getAttribute(Attributes.ATR_CAR_STORE.v());
        // do filter
        filter.doFilter(this.req, this.resp, this.chain);
        verify(this.req).getAttribute(Attributes.ATR_HB_SESSION.v());
        verify(this.req).getSession(false);
        verify(this.httpSession).getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        verify(this.req).getParameter(Attributes.PRM_CAR_STORE_ID.v());
        verify(this.carStore).get(222);
        verify(this.getCarFunction).apply(this.hbSession);
        verify(this.car).getOwner();
        verify(this.user).getId();
        verify(this.chain).doFilter(this.req, this.resp);
        // must happen nothing more
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.hbSession,
                this.req, this.resp, this.chain, this.httpSession,
                this.car, this.carStore, this.user, this.getCarFunction
        );
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void whenLoggedIdNotEqualToCarOwnerIdThenResponseToMainPageWithError() throws IOException, ServletException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn(111L);
        when(this.req.getParameter(Attributes.PRM_CAR_STORE_ID.v())).thenReturn("222");
        when(this.carStore.get(222)).thenReturn(this.getCarFunction);
        when(this.getCarFunction.apply(this.hbSession)).thenReturn(this.car);
        when(this.car.getOwner()).thenReturn(this.user);
        when(this.user.getId()).thenReturn(333L);   // other than logged
        // initialize
        var filter = new UserCanEditCarFilter();
        filter.init(this.fConfig);
        verify(this.fConfig).getServletContext();
        verify(this.sContext).getAttribute(Attributes.ATR_CAR_STORE.v());
        // do filter
        filter.doFilter(this.req, this.resp, this.chain);
        verify(this.req).getAttribute(Attributes.ATR_HB_SESSION.v());
        verify(this.req).getSession(false);
        verify(this.httpSession).getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        verify(this.req).getParameter(Attributes.PRM_CAR_STORE_ID.v());
        verify(this.carStore).get(222);
        verify(this.getCarFunction).apply(this.hbSession);
        verify(this.car).getOwner();
        verify(this.user).getId();
        // get redirect string
        verify(this.user).getLogin();
        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.resp).sendRedirect(redirectCaptor.capture());
        var redirect = redirectCaptor.getValue();
        assertTrue(redirect.contains(WebApp.BASEDIR.v()));
        assertTrue(redirect.contains(WebApp.MSG_ERROR.v()));
        // must happen nothing more
        verify(this.user, never()).getPassword();
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.hbSession,
                this.req, this.resp, this.chain, this.httpSession,
                this.car, this.carStore, this.user, this.getCarFunction
        );
    }

    @Test
    public void whenCarIdParameterNotParsableAsLongThenServletException() throws IOException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn(111L);
        when(this.req.getParameter(Attributes.PRM_CAR_STORE_ID.v())).thenReturn("not parsable as long");
        // initialize
        var filter = new UserCanEditCarFilter();
        filter.init(this.fConfig);
        verify(this.fConfig).getServletContext();
        verify(this.sContext).getAttribute(Attributes.ATR_CAR_STORE.v());
        // do filter
        var wasException = new boolean[]{false};
        try {
            filter.doFilter(this.req, this.resp, this.chain);
        } catch (ServletException e) {
            wasException[0] = true;
        }
        assertTrue(wasException[0]);
        verify(this.req).getSession(false);
        verify(this.httpSession).getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        verify(this.req).getParameter(Attributes.PRM_CAR_STORE_ID.v());
        // must happen nothing more
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.hbSession,
                this.req, this.resp, this.chain, this.httpSession,
                this.car, this.carStore, this.user, this.getCarFunction
        );
    }

    @Test
    public void whenCarNotFoundByIdThenServletException() throws IOException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn(111L);
        when(this.req.getParameter(Attributes.PRM_CAR_STORE_ID.v())).thenReturn("222");
        when(this.carStore.get(222)).thenReturn(this.getCarFunction);
        when(this.getCarFunction.apply(this.hbSession)).thenReturn(null);
        // initialize
        var filter = new UserCanEditCarFilter();
        filter.init(this.fConfig);
        verify(this.fConfig).getServletContext();
        verify(this.sContext).getAttribute(Attributes.ATR_CAR_STORE.v());
        // do filter
        var wasException = new boolean[]{false};
        try {
            filter.doFilter(this.req, this.resp, this.chain);
        } catch (ServletException e) {
            wasException[0] = true;
        }
        assertTrue(wasException[0]);
        verify(this.req).getAttribute(Attributes.ATR_HB_SESSION.v());
        verify(this.req).getSession(false);
        verify(this.httpSession).getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        verify(this.req).getParameter(Attributes.PRM_CAR_STORE_ID.v());
        verify(this.carStore).get(222);
        verify(this.getCarFunction).apply(this.hbSession);
        // must happen nothing more
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.hbSession,
                this.req, this.resp, this.chain, this.httpSession,
                this.car, this.carStore, this.user, this.getCarFunction
        );
    }


}