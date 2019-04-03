package carstore.filter;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class UserIsLoggedFilterTest {

    @Mock
    private ServletContext sContext;
    @Mock
    private FilterConfig fConfig;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private FilterChain chain;
    @Mock
    private HttpSession httpSession;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.req.getServletContext()).thenReturn(this.sContext);
        when(this.req.getSession(false)).thenReturn(this.httpSession);
        when(this.sContext.getContextPath()).thenReturn("root");
        when(this.req.getContextPath()).thenReturn("root");
    }

    @Test
    public void justForCoverageDestroy() {
        var filter = new UserIsLoggedFilter();
        filter.destroy();
    }

    @Test
    public void whenNoLoggedUserThenRedirectToLoginWithError() throws IOException, ServletException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn(null);
        // initialize
        var filter = new UserIsLoggedFilter();
        filter.init(this.fConfig);
        // do filter
        filter.doFilter(this.req, this.resp, this.chain);
        verify(this.req).getSession(false);
        verify(this.httpSession).getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        // get redirect string
        ArgumentCaptor<String> redirectCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.resp).sendRedirect(redirectCaptor.capture());
        var redirect = redirectCaptor.getValue();
        assertTrue(redirect.contains(WebApp.MSG_ERROR.v()));
    }

    @Test
    public void whenIsLoggedUserParameterThenDoFilter() throws IOException, ServletException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn(12L);
        // initialize
        var filter = new UserIsLoggedFilter();
        filter.init(this.fConfig);
        // do filter
        filter.doFilter(this.req, this.resp, this.chain);
        verify(this.req).getSession(false);
        verify(this.httpSession).getAttribute(Attributes.ATR_LOGGED_USER_ID.v());
        verify(this.chain).doFilter(this.req, this.resp);
        // must happen nothing more
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.httpSession,
                this.req, this.resp, this.chain
        );
    }

    @Test
    public void whenIdIsNotOfTypeLongThenServletException() throws IOException, ServletException {
        when(this.httpSession.getAttribute(Attributes.ATR_LOGGED_USER_ID.v())).thenReturn("not_long");
        // initialize
        var filter = new UserIsLoggedFilter();
        filter.init(this.fConfig);
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
        // must happen nothing more
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.httpSession,
                this.req, this.resp, this.chain
        );
    }

}