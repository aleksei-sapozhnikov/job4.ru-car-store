package carstore.store;

import carstore.model.Image;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;


public class ImageStoreTest {

    @Mock
    Session hbSession;
    @Mock
    Query query;
    @Mock
    Transaction tx;

    @Mock
    Image image;
    @Mock
    List<Image> imageList;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.hbSession.beginTransaction()).thenReturn(this.tx);
    }

    @Test
    public void whenGetThenSessionGetById() {
        long imageId = 3;
        when(this.hbSession.get(Image.class, imageId)).thenReturn(this.image);
        var store = new ImageStore();
        var result = store.get(imageId).apply(this.hbSession);
        assertSame(result, this.image);
    }

    @Test
    public void whenGetAllThenSessionQueryList() {
        when(this.hbSession.createQuery("from Image")).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.imageList);
        var store = new ImageStore();
        var result = store.getAll().apply(this.hbSession);
        assertSame(result, imageList);
    }

    @Test
    public void whenGetForCarThenListOfImagesForGivenCarId() {
        long carId = 5;
        when(this.hbSession.createQuery("from Image where car.id = :carId")).thenReturn(this.query);
        when(this.query.setParameter("carId", carId)).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.imageList);
        var store = new ImageStore();
        var result = store.getForCar(carId).apply(this.hbSession);
        assertSame(result, imageList);
    }
}