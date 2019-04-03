package carstore.filter;

import carstore.constants.Attributes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class HibernateSessionFilterTest {

    @Mock
    private ServletContext sContext;
    @Mock
    private FilterConfig fConfig;
    @Mock
    private SessionFactory sFactory;
    @Mock
    private Session session;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private FilterChain chain;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.fConfig.getServletContext()).thenReturn(this.sContext);
        when(this.sContext.getAttribute(Attributes.ATR_HB_FACTORY.v())).thenReturn(this.sFactory);
    }

    @Test
    public void justForCoverageDestroy() {
        var filter = new HibernateSessionFilter();
        filter.destroy();
    }

    @Test
    public void whenNoSessionFoundThenCreatingNewSession() throws IOException, ServletException {
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(null);
        var newSession = mock(Session.class);
        when(sFactory.openSession()).thenReturn(newSession);
        // initialize
        var filter = new HibernateSessionFilter();
        filter.init(this.fConfig);
        verify(this.fConfig).getServletContext();
        verify(this.sContext).getAttribute(Attributes.ATR_HB_FACTORY.v());
        // do filter
        filter.doFilter(this.req, this.resp, this.chain);
        verify(this.req).getAttribute(Attributes.ATR_HB_SESSION.v());
        verify(sFactory).openSession();
        verify(req).setAttribute(Attributes.ATR_HB_SESSION.v(), newSession);
        verify(this.chain).doFilter(this.req, this.resp);
        verify(newSession).close();
        verify(this.req).removeAttribute(Attributes.ATR_HB_SESSION.v());
        // must happen nothing more
        verify(this.session, never()).clear();
        verify(newSession, never()).clear();
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.sFactory,
                this.session, this.req, this.resp, this.chain, newSession
        );
    }

    @Test
    public void whenSessionExistsThenNotCreatingNewSession() throws IOException, ServletException {
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.session);
        // initialize
        var filter = new HibernateSessionFilter();
        filter.init(this.fConfig);
        verify(this.fConfig).getServletContext();
        verify(this.sContext).getAttribute(Attributes.ATR_HB_FACTORY.v());
        // do filter
        filter.doFilter(this.req, this.resp, this.chain);
        verify(this.req).getAttribute(Attributes.ATR_HB_SESSION.v());
        verify(this.chain).doFilter(this.req, this.resp);
        verify(this.session).close();
        verify(this.req).removeAttribute(Attributes.ATR_HB_SESSION.v());
        // must happen nothing more
        verify(this.session, never()).clear();
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.sFactory,
                this.session, this.req, this.resp, this.chain
        );
    }

    @Test
    public void whenExceptionThrownThenSessionClearAndThrowTheException() throws IOException, ServletException {
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.session);
        Mockito.doThrow(new ServletException("expected")).when(this.chain).doFilter(any(), any());
        // initialize
        var filter = new HibernateSessionFilter();
        filter.init(this.fConfig);
        verify(this.fConfig).getServletContext();
        verify(this.sContext).getAttribute(Attributes.ATR_HB_FACTORY.v());
        // do filter
        try {
            filter.doFilter(this.req, this.resp, this.chain);
        } catch (Exception e) {
            assertEquals("expected", e.getMessage());
        }
        verify(this.req).getAttribute(Attributes.ATR_HB_SESSION.v());
        verify(this.chain).doFilter(this.req, this.resp);
        verify(this.session).clear();
        verify(this.session).close();
        verify(this.req).removeAttribute(Attributes.ATR_HB_SESSION.v());
        // must happen nothing more
        verifyNoMoreInteractions(
                this.sContext, this.fConfig, this.sFactory,
                this.session, this.req, this.resp, this.chain
        );
    }


}