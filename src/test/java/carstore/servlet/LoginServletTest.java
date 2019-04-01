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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LoginServletTest {
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
    private HttpSession httpSession;
    @Mock
    private RequestDispatcher rDispatcher;
    @Mock
    private UserStore userStore;
    @Mock
    private User user;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.sConfig.getServletContext()).thenReturn(this.sContext);
        when(this.sContext.getContextPath()).thenReturn("root");
        when(this.req.getContextPath()).thenReturn("root");
        when(this.sContext.getAttribute(Attributes.ATR_USER_STORE.v())).thenReturn(this.userStore);
        when(this.req.getRequestDispatcher(any(String.class))).thenReturn(this.rDispatcher);
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.hbSession);
        when(this.req.getSession()).thenReturn(this.httpSession);
    }

    @Test
    public void justForCoverageDestroy() {
        var servlet = new LoginServlet();
        servlet.destroy();
    }

    @Test
    public void whenDoGetThenForwardToLoginPage() throws ServletException, IOException {
        var servlet = new LoginServlet();
        servlet.doGet(req, resp);
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.req).getRequestDispatcher(pathCaptor.capture());
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.VIEW_ROOT.v()));
        assertTrue(path.contains(WebApp.PG_LOGIN.v()));
        verify(this.rDispatcher).forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenRightLoginAndPasswordThenAttachUserIdToSession() throws ServletException, IOException {
        var login = "userLogin";
        var password = "userPassword";
        when(this.req.getParameter(Attributes.PRM_USER_LOGIN.v())).thenReturn(login);
        when(this.req.getParameter(Attributes.PRM_USER_PASSWORD.v())).thenReturn(password);
        var getFunction = (Function<Session, User>) Mockito.mock(Function.class);
        when(this.userStore.getByCredentials(login, password)).thenReturn(getFunction);
        when(getFunction.apply(this.hbSession)).thenReturn(this.user);   // user found
        var userId = 115L;
        when(this.user.getId()).thenReturn(userId);
        // actions
        var servlet = new LoginServlet();
        servlet.init(this.sConfig);
        servlet.doPost(this.req, this.resp);
        // verify
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.resp).sendRedirect(pathCaptor.capture());
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.MSG_SUCCESS.v()));
        verify(this.httpSession).setAttribute(Attributes.ATR_LOGGED_USER_ID.v(), userId);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenWrongLoginAndPasswordThenErrorAndDoGet() throws ServletException, IOException {
        var login = "userLogin";
        var password = "userPassword";
        when(this.req.getParameter(Attributes.PRM_USER_LOGIN.v())).thenReturn(login);
        when(this.req.getParameter(Attributes.PRM_USER_PASSWORD.v())).thenReturn(password);
        var getFunction = (Function<Session, User>) Mockito.mock(Function.class);
        when(this.userStore.getByCredentials(login, password)).thenReturn(getFunction);
        when(getFunction.apply(this.hbSession)).thenReturn(null);   // user not found
        // actions
        var servlet = new LoginServlet() {
            @Override
            public void doGet(HttpServletRequest req, HttpServletResponse resp) {
                req.setAttribute("Entered doGet method", true);
            }
        };
        servlet.init(this.sConfig);
        servlet.doPost(this.req, this.resp);
        // verify
        verify(this.req).setAttribute(eq(WebApp.MSG_ERROR.v()), any(String.class));
        verify(this.req).setAttribute("Entered doGet method", true);
    }

}