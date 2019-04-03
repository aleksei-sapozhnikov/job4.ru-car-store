package carstore.util;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({Utils.class})
public class UtilsTestWithPowermock {

    @Mock
    private Logger logger;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenResourceNotExistingThenLogErrorMessageAndReturnEmptyArray() throws URISyntaxException, IOException {
        var defaultLogger = Whitebox.getInternalState(Utils.class, "LOG");
        Whitebox.setInternalState(Utils.class, "LOG", this.logger);
        // do actions
        var result = Utils.readResource(this.getClass(), "wrong resource reference");
        verify(this.logger).fatal(any(String.class), any(Throwable.class));
        assertArrayEquals(result, new byte[0]);
        // clear after test
        Whitebox.setInternalState(Utils.class, "LOG", defaultLogger);
    }
}