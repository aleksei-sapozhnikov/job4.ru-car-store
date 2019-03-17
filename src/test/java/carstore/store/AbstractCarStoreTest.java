package carstore.store;

import carstore.model.car.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class AbstractCarStoreTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Car car;

    private AbstractCarStore store;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(this.sessionFactory.openSession()).thenReturn(this.session);
        when(this.session.beginTransaction()).thenReturn(this.transaction);
        this.store = new AbstractCarStore(this.sessionFactory) {
        };
    }

    @Test
    public void whenMergeThenSessionMergeCarAndReturnResult() {
        this.store.merge(this.car);
        var stored = mock(Car.class);
        when(this.session.merge(this.car)).thenReturn(stored);
        assertEquals(this.store.merge(car), stored);
    }

    @Test
    public void whenGetThenSessionGetByCarId() {
        this.store.get(this.car);
        var carOne = mock(Car.class);
        when(this.session.get(Car.class, this.car.getId())).thenReturn(carOne);
    }

    @Test
    public void whenDeleteThenSessionDeleteByCar() {
        this.store.delete(this.car);
        verify(this.session).delete(this.car);
    }

    @Test
    public void whenGetAllThenSessionQueryFromCarList() {
        var query = Mockito.mock(Query.class);
        when(this.session.createQuery("from Car")).thenReturn(query);
        var carOne = mock(Car.class);
        var carTwo = mock(Car.class);
        var carThree = mock(Car.class);
        when(query.list()).thenReturn(List.of(carOne, carTwo, carThree));
        assertEquals(this.store.getAll(), List.of(carOne, carTwo, carThree));
    }

    @Test
    public void whenClearThenSessionDeleteFromCar() {
        var query = Mockito.mock(Query.class);
        when(this.session.createQuery("delete from Car")).thenReturn(query);
        this.store.clear();
        verify(query).executeUpdate();
    }

    @Test
    public void whenExceptionThenTransactionRollback() {
        doThrow(new RuntimeException("Expected that!")).when(this.transaction).commit();
        String msg = null;
        try {
            this.store.delete(this.car);
        } catch (RuntimeException e) {
            msg = e.getMessage();
        }
        assertEquals("Expected that!", msg);
        verify(this.transaction).rollback();
    }

    @Test
    public void whenCloseThenFactoryClosed() {
        this.store.close();
        verify(this.sessionFactory).close();
    }
}