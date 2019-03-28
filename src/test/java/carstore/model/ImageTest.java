package carstore.model;

import org.junit.Test;

import static org.junit.Assert.*;


public class ImageTest {

    @Test
    public void whenCreatedUsingStaticOfMethodThenGetValuesRight() {
        var image = Image.of(new byte[]{0, 1, 2});
        assertArrayEquals(image.getData(), new byte[]{0, 1, 2});
    }

    @Test
    public void whenValuesSetBySettersThenGetNewValues() {
        var image = Image.of(new byte[]{0, 1, 2});
        image.setData(new byte[]{9, 8, 7, 6, 5});
        assertArrayEquals(image.getData(), new byte[]{9, 8, 7, 6, 5});
    }

    @Test
    public void whenSetDataArrayThenDataArrayIsCopiedToNewObject() {
        var input = new byte[]{0, 1, 2, 3};
        var image = Image.of(input);
        assertArrayEquals(image.getData(), new byte[]{0, 1, 2, 3});
        assertNotSame(input, image.getData());
        input[0] = 100;
        assertArrayEquals(input, new byte[]{100, 1, 2, 3});
        assertArrayEquals(image.getData(), new byte[]{0, 1, 2, 3});
        //
        var output = image.getData();
        assertNotSame(image.getData(), output);
        assertArrayEquals(output, new byte[]{0, 1, 2, 3});
        assertArrayEquals(image.getData(), new byte[]{0, 1, 2, 3});
        output[0] = 55;
        assertArrayEquals(output, new byte[]{55, 1, 2, 3});
        assertArrayEquals(image.getData(), new byte[]{0, 1, 2, 3});
    }

    @Test
    public void testEqualsAndHashcode() {
        var main = Image.of(new byte[]{0, 1, 2, 3});
        var same = Image.of(new byte[]{0, 1, 2, 3});
        var otherData = Image.of(new byte[]{5, 4, 5});
        // trivial
        assertEquals(main, main);
        assertEquals(main.hashCode(), main.hashCode());
        assertNotEquals(main, null);
        assertNotEquals(main, "other class");
        // equal
        assertEquals(main, same);
        assertEquals(main.hashCode(), same.hashCode());
        // not equal
        assertNotEquals(main, otherData);
    }
}