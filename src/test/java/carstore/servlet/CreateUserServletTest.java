package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import carstore.model.User;
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
import java.io.IOException;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CreateUserServletTest {

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
    private UserStore userStore;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.sConfig.getServletContext()).thenReturn(this.sContext);
        when(this.sContext.getContextPath()).thenReturn("root");
        when(this.req.getContextPath()).thenReturn("root");
        when(this.sContext.getAttribute(Attributes.ATR_USER_STORE.v())).thenReturn(this.userStore);
        when(this.req.getRequestDispatcher(any(String.class))).thenReturn(this.rDispatcher);
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.hbSession);
    }

    @Test
    public void justForCoverageDestroy() {
        var servlet = new CreateUserServlet();
        servlet.destroy();
    }

    @Test
    public void whenDoGetThenForwardToCreateUserPage() throws ServletException, IOException {
        var servlet = new CreateUserServlet();
        servlet.doGet(req, resp);
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.req).getRequestDispatcher(pathCaptor.capture());
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.VIEW_ROOT.v()));
        assertTrue(path.contains(WebApp.PG_CREATE_USER.v()));
        verify(this.rDispatcher).forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenDoPostThenUserCreatedAndRedirectMainPage() throws ServletException, IOException {
        when(this.req.getParameter(Attributes.PRM_USER_LOGIN.v())).thenReturn("userLogin");
        when(this.req.getParameter(Attributes.PRM_USER_PASSWORD.v())).thenReturn("userPassword");
        when(this.req.getParameter(Attributes.PRM_USER_PHONE.v())).thenReturn("userPhone");
        var saveFunction = (Function<Session, Boolean>) Mockito.mock(Function.class);
        when(this.userStore.saveIfNotExists(any(User.class))).thenReturn(saveFunction);
        when(saveFunction.apply(this.hbSession)).thenReturn(true);   // user saved
        //
        var servlet = new CreateUserServlet();
        servlet.init(this.sConfig);
        servlet.doPost(this.req, this.resp);
        //
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.resp).sendRedirect(pathCaptor.capture());
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.MSG_SUCCESS.v()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenDoPostButUserNotCreatedThenRedirectMainPageWithError() throws ServletException, IOException {
        // init mocks
        when(this.req.getParameter(Attributes.PRM_USER_LOGIN.v())).thenReturn("userLogin");
        when(this.req.getParameter(Attributes.PRM_USER_PASSWORD.v())).thenReturn("userPassword");
        when(this.req.getParameter(Attributes.PRM_USER_PHONE.v())).thenReturn("userPhone");
        var saveFunction = (Function<Session, Boolean>) Mockito.mock(Function.class);
        when(this.userStore.saveIfNotExists(any(User.class))).thenReturn(saveFunction);
        when(saveFunction.apply(this.hbSession)).thenReturn(false);     // user not saved
        // create servlet. Overriding doGet do check if it was called
        var servlet = new CreateUserServlet() {
            @Override
            public void doGet(HttpServletRequest req, HttpServletResponse resp) {
                req.setAttribute("Was called", true);
            }
        };
        // do actions
        servlet.init(this.sConfig);
        servlet.doPost(this.req, this.resp);
        // verify Error attribute set
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.req).setAttribute(pathCaptor.capture(), any(String.class));
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.MSG_ERROR.v()));
        // verify calling of this.doGet()
        verify(this.req).setAttribute("Was called", true);
    }

}