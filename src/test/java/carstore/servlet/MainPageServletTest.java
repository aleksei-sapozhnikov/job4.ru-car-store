package carstore.servlet;

import carstore.constants.WebApp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPageServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private RequestDispatcher rDispatcher;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.req.getRequestDispatcher(any(String.class))).thenReturn(this.rDispatcher);
    }

    @Test
    public void justForCoverageInitDestroy() throws ServletException {
        var servlet = new MainPageServlet();
        servlet.init();
        servlet.destroy();
    }

    @Test
    public void whenDoGetThenForwardToShowAllPage() throws ServletException, IOException {
        var servlet = new MainPageServlet();
        servlet.doGet(req, resp);
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.req).getRequestDispatcher(pathCaptor.capture());
        var path = pathCaptor.getValue();
        assertTrue(path.contains(WebApp.VIEW_ROOT.v()));
        verify(this.rDispatcher).forward(req, resp);
    }
}