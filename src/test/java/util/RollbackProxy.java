package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mockito.Mockito;

import java.lang.reflect.Proxy;

/**
 * Class with static methods to create proxies
 * doing rollback after all checks finished.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
public class RollbackProxy {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(RollbackProxy.class);

    /**
     * Returns proxy Hibernate Session which
     * actually doesn't give transaction and doesn't
     * close.
     * <p>
     * Method "clear" is used to make rollback operation.
     *
     * @param session Session to wrap.
     * @return Proxy Session object.
     */
    public static Session create(Session session) {
        return (Session) Proxy.newProxyInstance(
                RollbackProxy.class.getClassLoader(),
                new Class[]{
                        Session.class
                },
                (proxy, method, args) -> {
                    Object result;
                    if ("beginTransaction".equals(method.getName())) {
                        result = Mockito.mock(Transaction.class);
                    } else if ("close".equals(method.getName())) {
                        result = null;
                    } else if ("clear".equals(method.getName())) {
                        session.getTransaction().rollback();
                        result = null;
                    } else {
                        result = method.invoke(session, args);
                    }
                    return result;
                }
        );
    }

    /**
     * Returns proxy Hibernate SessionFactory.
     * The factory is intended for operations where several
     * sessions and transactions are opened and then closed.
     * <p>
     * Created sessions are proxies which doesn't actually open
     * transactions and make commit. The only real session
     * is one created inside the method.
     * <p>
     * When all transactions are finished, call session.close()
     * to rollback all operations made in this session and
     * transaction.
     *
     * @param factory SessionFactory to wrap.
     * @return Proxy SessionFactory object.
     */
    public static SessionFactory create(SessionFactory factory) {
        var session = factory.openSession();
        session.beginTransaction();
        return (SessionFactory) Proxy.newProxyInstance(
                RollbackProxy.class.getClassLoader(),
                new Class[]{
                        SessionFactory.class
                },
                (proxy, method, args) -> {
                    Object result;
                    if ("openSession".equals(method.getName())) {
                        result = RollbackProxy.create(session);
                    } else if ("close".equals(method.getName())) {
                        session.getTransaction().rollback();
                        session.close();
                        result = null;
                    } else {
                        result = method.invoke(factory, args);
                    }
                    return result;
                }
        );
    }
}
