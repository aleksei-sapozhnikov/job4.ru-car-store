package carstore.store;

import carstore.model.Car;
import carstore.model.Image;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;


public class CarStoreTest {

    @Mock
    Session hbSession;
    @Mock
    Query query;
    @Mock
    Transaction tx;

    @Mock
    Image img1, img2, img3;

    @Mock
    Car car;
    @Mock
    List<Car> carList;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.hbSession.beginTransaction()).thenReturn(this.tx);
    }

    @Test
    public void whenGetThenSessionGetById() {
        long carId = 5;
        when(this.hbSession.get(Car.class, carId)).thenReturn(this.car);
        var store = new CarStore();
        var result = store.get(carId).apply(this.hbSession);
        assertSame(result, this.car);
    }

    @Test
    public void whenGetAllThenSessionQueryList() {
        when(this.hbSession.createQuery("from Car")).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.carList);
        var store = new CarStore();
        var result = store.getAll().apply(this.hbSession);
        assertSame(result, carList);
    }

    @Test
    public void whenSaveOrUpdateCarWithImagesThenSaved() {
        long carId = 7;
        var images = Set.of(this.img1, this.img2, this.img3);
        when(this.car.getId()).thenReturn(carId);
        when(this.hbSession.createQuery("delete from Image where car.id = :carId")).thenReturn(this.query);
        when(this.query.setParameter("carId", carId)).thenReturn(this.query);
        // actions
        var store = new CarStore();
        store.saveOrUpdate(this.car, images).accept(this.hbSession);
        // verify
        verify(this.query).executeUpdate();
        verify(this.hbSession).saveOrUpdate(this.car);
        images.forEach(img -> {
            verify(img).setCar(this.car);
            this.hbSession.save(img);
        });
    }

    @Test
    public void whenNoImagesThenNoImageDeletion() {
        long carId = 7;
        var images = Collections.<Image>emptySet();
        when(this.car.getId()).thenReturn(carId);
        when(this.hbSession.createQuery("delete from Image where car.id = :carId")).thenReturn(this.query);
        when(this.query.setParameter("carId", carId)).thenReturn(this.query);
        // actions
        var store = new CarStore();
        store.saveOrUpdate(this.car, images).accept(this.hbSession);
        // verify
        verify(this.query, never()).executeUpdate();
    }
}