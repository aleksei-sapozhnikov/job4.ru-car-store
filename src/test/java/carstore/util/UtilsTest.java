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

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({Utils.class})
public class UtilsTest {

    public static final String SAMPLE_RESOURCE_REFERENCE = "carstore/util/sampleResource";

    @Mock
    private Logger logger;
    @Mock
    private Part part;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenInputStreamGivenThenReadFullyToOutputStream() throws IOException {
        try (var in = new ByteArrayInputStream("test".getBytes());
             var out = new ByteArrayOutputStream()) {
            Utils.readFullInput(in, out);
            assertEquals("test", out.toString());
        }
    }

    @Test
    public void whenStringOfLongThenParsed() {
        var str = "12345678";
        var result = Utils.parseLong(str, -1);
        assertEquals(result, 12345678);
    }

    @Test
    public void whenStringNotOfLongThenDefaultValue() {
        var str = "123a43";
        var result = Utils.parseLong(str, -1);
        assertEquals(result, -1);
    }

    @Test
    public void whenReadExistingResourceThenResourceInformationReturned() throws URISyntaxException, IOException {
        var resourcePath = Path.of(this.getClass().getClassLoader().getResource(SAMPLE_RESOURCE_REFERENCE).toURI());
        var infoStr = "Sample resource information";
        Files.writeString(resourcePath, infoStr, StandardOpenOption.TRUNCATE_EXISTING);
        var result = Utils.readResource(this.getClass(), SAMPLE_RESOURCE_REFERENCE);
        assertArrayEquals(result, infoStr.getBytes());
    }

    @Test
    public void whenResourceNotExistingThenLogErrorMessageAndReturnEmptyArray() throws URISyntaxException, IOException {
        var defaultLogger = Whitebox.getInternalState(Utils.class, "LOG");
        var utilsObj = new Utils();
        Whitebox.setInternalState(Utils.class, "LOG", this.logger);
        // do actions
        var result = Utils.readResource(this.getClass(), "wrong resource reference");
        verify(this.logger).fatal(any(String.class), any(Throwable.class));
        assertArrayEquals(result, new byte[0]);
        // clear after test
        Whitebox.setInternalState(Utils.class, "LOG", defaultLogger);
    }

    @Test
    public void whenReadStringFromPartThenFullyRead() throws IOException {
        try (var in = new ByteArrayInputStream("test".getBytes())) {
            when(this.part.getInputStream()).thenReturn(in);
            var result = Utils.readString(this.part);
            assertEquals("test", result);
        }
    }

    @Test
    public void whenPartInfoCanBeParsedToIntegerThenInteger() throws IOException, ServletException {
        try (var in = new ByteArrayInputStream("12345".getBytes())) {
            when(this.part.getInputStream()).thenReturn(in);
            var result = Utils.readInteger(this.part);
            assertEquals(12345, (int) result);
        }
    }

    @Test
    public void whenPartInfoCannotBeParsedToIntegerThenServletException() throws IOException, ServletException {
        var wasException = new boolean[]{false};
        try (var in = new ByteArrayInputStream("12345678901234456789".getBytes())) {
            when(this.part.getInputStream()).thenReturn(in);
            Utils.readInteger(this.part);
        } catch (ServletException e) {
            wasException[0] = true;
        }
        assertTrue(wasException[0]);
    }

    @Test
    public void whenPartInfoCanBeParsedToLongThenInteger() throws IOException, ServletException {
        try (var in = new ByteArrayInputStream("1234567890123445678".getBytes())) {
            when(this.part.getInputStream()).thenReturn(in);
            var result = Utils.readLong(this.part);
            assertEquals(1234567890123445678L, (long) result);
        }
    }

    @Test
    public void whenPartInfoCannotBeParsedToLongThenServletException() throws IOException, ServletException {
        var wasException = new boolean[]{false};
        try (var in = new ByteArrayInputStream("asdfasdf".getBytes())) {
            when(this.part.getInputStream()).thenReturn(in);
            Utils.readLong(this.part);
        } catch (ServletException e) {
            wasException[0] = true;
        }
        assertTrue(wasException[0]);
    }

    @Test
    public void whenReadByteArrayThenResult() throws IOException {
        try (var in = new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5})) {
            when(this.part.getInputStream()).thenReturn(in);
            var result = Utils.readByteArray(this.part);
            assertArrayEquals(result, new byte[]{1, 2, 3, 4, 5});
        }
    }
}