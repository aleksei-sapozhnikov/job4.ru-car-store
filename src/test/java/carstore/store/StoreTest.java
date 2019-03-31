package carstore.store;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class StoreTest {

    private Store store = new Store() {
    };

    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Function<Session, Object> function;
    @Mock
    private Consumer<Session> consumer;
    @Mock
    private Object result;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.session.beginTransaction()).thenReturn(this.transaction);
    }

    @Test
    public void whenFunctionTransactionThenOperationsApplied() {
        when(this.function.apply(this.session)).thenReturn(this.result);
        var result = this.store.functionTransaction(this.function).apply(this.session);
        assertSame(result, this.result);
        verify(this.session).beginTransaction();
        verify(this.function).apply(this.session);
        verify(this.transaction).commit();
    }

    @Test
    public void whenFunctionTransactionExceptionThrownThenTransactionRollbackAndThrowException() {
        when(this.function.apply(this.session)).thenThrow(new RuntimeException("expected"));
        var wasException = new boolean[]{false};
        try {
            this.store.functionTransaction(this.function).apply(this.session);
        } catch (RuntimeException e) {
            assertEquals("expected", e.getMessage());
            wasException[0] = true;
        }
        assertTrue(wasException[0]);
        verify(this.session).beginTransaction();
        verify(this.transaction).rollback();
    }

    @Test
    public void whenConsumerTransactionThenOperationsApplied() {
        this.store.consumerTransaction(this.consumer).accept(this.session);
        verify(this.session).beginTransaction();
        verify(this.consumer).accept(this.session);
        verify(this.transaction).commit();
    }

    @Test
    public void whenConsumerTransactionExceptionThrownThenTransactionRollbackAndThrowException() {
        doThrow(new RuntimeException("expected")).when(this.consumer).accept(this.session);
        var wasException = new boolean[]{false};
        try {
            this.store.consumerTransaction(this.consumer).accept(this.session);
        } catch (RuntimeException e) {
            assertEquals("expected", e.getMessage());
            wasException[0] = true;
        }
        assertTrue(wasException[0]);
        verify(this.session).beginTransaction();
        verify(this.transaction).rollback();
    }
}