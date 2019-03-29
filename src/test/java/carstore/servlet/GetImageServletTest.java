package carstore.servlet;

import carstore.constants.Attributes;
import carstore.model.Image;
import carstore.store.ImageStore;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class GetImageServletTest {

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
    private Image image;
    @Mock
    private ImageStore imageStore;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.sConfig.getServletContext()).thenReturn(this.sContext);
        when(this.sContext.getAttribute(Attributes.ATR_IMAGE_STORE.v())).thenReturn(this.imageStore);
        when(this.req.getAttribute(Attributes.ATR_HB_SESSION.v())).thenReturn(this.hbSession);
    }

    @Test
    public void justForCoverageDestroy() {
        var servlet = new GetImageServlet();
        servlet.destroy();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenDoGetThenReturnImageDataById() throws ServletException, IOException {
        when(this.req.getParameter(Attributes.PRM_IMAGE_ID.v())).thenReturn("25");
        var getFunction = (Function<Session, Image>) Mockito.mock(Function.class);
        when(this.imageStore.get(25)).thenReturn(getFunction);
        when(getFunction.apply(this.hbSession)).thenReturn(this.image);     // image found
        when(this.image.getData()).thenReturn(new byte[]{1, 3, 5});
        var output = Mockito.mock(ServletOutputStream.class);
        when(this.resp.getOutputStream()).thenReturn(output);
        // actions
        var servlet = new GetImageServlet();
        servlet.init(this.sConfig);
        servlet.doGet(this.req, this.resp);
        // verify
        verify(output).write(new byte[]{1, 3, 5});
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenDoGetAndImageNotFoundThenWriteDefaultImage() throws ServletException, IOException {
        when(this.req.getParameter(Attributes.PRM_IMAGE_ID.v())).thenReturn("25");
        var getFunction = (Function<Session, Image>) Mockito.mock(Function.class);
        when(this.imageStore.get(25)).thenReturn(getFunction);
        when(getFunction.apply(this.hbSession)).thenReturn(null);       // image not found
        var output = Mockito.mock(ServletOutputStream.class);
        when(this.resp.getOutputStream()).thenReturn(output);
        // actions
        var servlet = new GetImageServlet();
        servlet.init(this.sConfig);
        servlet.doGet(this.req, this.resp);
        // verify
        verify(output).write(GetImageServlet.getNotFoundImage());
    }
}