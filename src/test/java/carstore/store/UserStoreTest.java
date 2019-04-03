package carstore.store;

import carstore.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class UserStoreTest {

    @Mock
    Session hbSession;
    @Mock
    Query query;
    @Mock
    Transaction tx;

    @Mock
    User user;
    @Mock
    List<User> userList;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(this.hbSession.beginTransaction()).thenReturn(this.tx);
    }

    @Test
    public void whenGetThenSessionGetById() {
        long userId = 6;
        when(this.hbSession.get(User.class, userId)).thenReturn(this.user);
        var store = new UserStore();
        var result = store.get(userId).apply(this.hbSession);
        assertSame(result, this.user);
    }

    @Test
    public void whenGetAllThenSessionQueryList() {
        when(this.hbSession.createQuery("from User")).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.userList);
        var store = new UserStore();
        var result = store.getAll().apply(this.hbSession);
        assertSame(result, userList);
    }

    @Test
    public void whenGetByCredentialsFoundThenUser() {
        var login = "log";
        var password = "pass";
        when(this.hbSession.createQuery("from User where login = :login and password = :password")).thenReturn(this.query);
        when(this.query.setParameter("login", login)).thenReturn(this.query);
        when(this.query.setParameter("password", password)).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.userList);
        when(this.userList.get(0)).thenReturn(this.user);
        // actions
        var store = new UserStore();
        // when user found
        when(this.userList.isEmpty()).thenReturn(false);
        var result = store.getByCredentials(login, password).apply(this.hbSession);
        assertEquals(result, this.user);
    }

    @Test
    public void whenGetByCredentialsNotFoundThenNull() {
        var login = "log";
        var password = "pass";
        when(this.hbSession.createQuery("from User where login = :login and password = :password")).thenReturn(this.query);
        when(this.query.setParameter("login", login)).thenReturn(this.query);
        when(this.query.setParameter("password", password)).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.userList);
        when(this.userList.get(0)).thenReturn(this.user);
        // actions
        var store = new UserStore();
        // when user not found
        when(this.userList.isEmpty()).thenReturn(true);
        var result = store.getByCredentials(login, password).apply(this.hbSession);
        assertNull(result);
    }

    @Test
    public void whenSaveAndUserNotExistedThenTrue() {
        var saveLogin = "user";
        when(this.user.getLogin()).thenReturn(saveLogin);
        when(this.hbSession.createQuery("from User where login = :login")).thenReturn(this.query);
        when(this.query.setParameter("login", saveLogin)).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.userList);
        // actions
        var store = new UserStore();
        // user not existed
        when(this.userList.isEmpty()).thenReturn(true);
        var saved = store.saveIfNotExists(this.user).apply(this.hbSession);
        assertTrue(saved);
        verify(this.hbSession).persist(this.user);
    }

    @Test
    public void whenSaveButUserExistedThenFalse() {
        var saveLogin = "user";
        when(this.user.getLogin()).thenReturn(saveLogin);
        when(this.hbSession.createQuery("from User where login = :login")).thenReturn(this.query);
        when(this.query.setParameter("login", saveLogin)).thenReturn(this.query);
        when(this.query.list()).thenReturn(this.userList);
        // actions
        var store = new UserStore();
        // user existed
        when(this.userList.isEmpty()).thenReturn(false);
        var saved = store.saveIfNotExists(this.user).apply(this.hbSession);
        assertFalse(saved);
        verify(this.hbSession, never()).persist(this.user);
    }
}
