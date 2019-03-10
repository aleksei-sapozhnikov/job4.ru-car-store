package t;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleTest {

    @Test
    public void testByTwo() {
        assertThat(new Simple().byTwo(2), is(4));
    }
}