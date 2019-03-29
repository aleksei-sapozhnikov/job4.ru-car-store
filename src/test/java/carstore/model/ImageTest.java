package carstore.model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;


public class ImageTest {

    @Test
    public void whenCreatedUsingStaticOfMethodThenGetValuesRight() {
        var car = Mockito.mock(Car.class);
        var image = Image.of(new byte[]{0, 1, 2}, car);
        assertEquals(0, image.getId());
        assertArrayEquals(image.getData(), new byte[]{0, 1, 2});
        assertSame(image.getCar(), car);
        //
        image = Image.of(new byte[]{5, 6, 7});
        assertEquals(0, image.getId());
        assertArrayEquals(image.getData(), new byte[]{5, 6, 7});
        assertNull(image.getCar());
    }

    @Test
    public void whenValuesSetBySettersThenGetNewValues() {
        var car = Mockito.mock(Car.class);
        var image = Image.of(new byte[]{0, 1, 2}, car);
        image.setId(56);
        assertEquals(56, image.getId());
        image.setData(new byte[]{9, 8, 7, 6, 5});
        assertArrayEquals(image.getData(), new byte[]{9, 8, 7, 6, 5});
        var newCar = Mockito.mock(Car.class);
        image.setCar(newCar);
        assertSame(image.getCar(), newCar);
    }

    @Test
    public void whenSetDataArrayThenDataArrayIsCopiedToNewObject() {
        var car = Mockito.mock(Car.class);
        var input = new byte[]{0, 1, 2, 3};
        var image = Image.of(input, car);
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

//    @Test
//    public void testEqualsAndHashcode() {
//        var car = Mockito.mock(Car.class);
//        var main = Image.of(new byte[]{0, 1, 2, 3}, car);
//        var same = Image.of(new byte[]{0, 1, 2, 3}, car);
//        var otherData = Image.of(new byte[]{5, 4, 5}, car);
//        var otherCar = Image.of(new byte[]{0, 1, 2, 3}, Mockito.mock(Car.class));
//        // trivial
//        assertEquals(main, main);
//        assertEquals(main.hashCode(), main.hashCode());
//        assertNotEquals(main, null);
//        assertNotEquals(main, "other class");
//        // equal
//        assertEquals(main, same);
//        assertEquals(main.hashCode(), same.hashCode());
//        // not equal
//        assertNotEquals(main, otherData);
//        assertNotEquals(main, otherCar);
//    }
}