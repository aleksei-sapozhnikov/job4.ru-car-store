package carstore.servlet;

import carstore.constants.ConstContext;
import carstore.model.User;
import carstore.store.NewUserStore;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import util.RollbackProxy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;


public class CreateUserServletTest {

    private final ServletContext context = mock(ServletContext.class);
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private final NewUserStore userStore = new NewUserStore();

    private final CreateUserServlet servlet = new CreateUserServlet() {
        @Override
        public ServletContext getServletContext() {
            return CreateUserServletTest.this.context;
        }
    };

    @Before
    public void initContext() {
        when(this.context.getAttribute(ConstContext.SESSION_FACTORY.v())).thenReturn(this.factory);
        when(this.context.getAttribute(ConstContext.USER_STORE.v())).thenReturn(this.userStore);
        this.servlet.init();
    }

    private User createUser(String login, String password, String phone) {
        var params = new HashMap<String, String>();
        params.put(User.Params.LOGIN.v(), login);
        params.put(User.Params.PASSWORD.v(), password);
        params.put(User.Params.PHONE.v(), phone);
        return User.from(params);
    }

    private void doIntegralTestWithRollback(Consumer<Session> operations) {
        try (var factory = RollbackProxy.create(this.factory);
             var session = factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                operations.accept(session);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    @Test
    public void whenUserNotInStorageThenSavedAndRedirect() throws IOException, ServletException {
        var req = mock(HttpServletRequest.class);
        var resp = mock(HttpServletResponse.class);
        when(req.getParameter("user_login")).thenReturn("login one");
        when(req.getParameter("user_password")).thenReturn("password one");
        when(req.getParameter("user_phone")).thenReturn("phone one");
        this.servlet.doPost(req, resp);
        verify(resp).sendRedirect(startsWith("Success:"));
    }

    @Test
    public void whenUserExistsInStorageThenErrorAttributeError() throws IOException, ServletException {
        try (var session = this.factory.openSession()) {
            var tx = session.beginTransaction();
            try {
                session.save(this.createUser("login one", "password two", "phone three"));
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        var req = mock(HttpServletRequest.class);
        var resp = mock(HttpServletResponse.class);
        var dispacther = mock(RequestDispatcher.class);
        when(req.getParameter("user_login")).thenReturn("login one");
        when(req.getParameter("user_password")).thenReturn("password one");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispacther);
        this.servlet.doPost(req, resp);
        verify(req).setAttribute(eq("error"), anyString());
        verify(dispacther).forward(req, resp);
    }

    @Test
    public void doPost() {
    }
}