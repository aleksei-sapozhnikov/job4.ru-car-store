package carstore.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.*;
import java.io.IOException;

import static org.mockito.Mockito.verify;

public class ResponseParametersFilterTest {

    @Mock
    private ServletRequest req;
    @Mock
    private ServletResponse resp;
    @Mock
    private FilterChain fChain;
    @Mock
    private FilterConfig fConfig;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void justForCoverageInitDestroy() {
        var filter = new ResponseParametersFilter();
        filter.init(this.fConfig);
        filter.destroy();
    }

    @Test
    public void whenDoFilterThenResponseParametersSet() throws IOException, ServletException {
        var filter = new ResponseParametersFilter();
        filter.doFilter(this.req, this.resp, this.fChain);
        verify(this.resp).setCharacterEncoding("UTF-8");
        verify(this.resp).setContentType("text/html");
    }
}