package carstore.listener;

import carstore.constants.Attributes;
import carstore.factory.CarFactory;
import carstore.factory.FrontItemFactory;
import carstore.factory.ImageFactory;
import carstore.store.CarStore;
import carstore.store.ImageStore;
import carstore.store.UserStore;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({
        ContextListener.class,
        Gson.class  // because it's final class
})
public class ContextListenerTest {

    @Mock
    ServletContextEvent sce;
    @Mock
    ServletContext ctx;

    @Mock
    Configuration configuration;
    @Mock
    SessionFactory sessionFactory;

    @Mock
    UserStore userStore;
    @Mock
    ImageStore imageStore;
    @Mock
    CarStore carStore;
    @Mock
    FrontItemFactory frontItemFactory;
    @Mock
    Gson gson;
    @Mock
    CarFactory carFactory;
    @Mock
    ImageFactory imageFactory;

    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(this.sce.getServletContext()).thenReturn(this.ctx);
    }


    @Test
    public void whenContextInitializedThenAttributesCreatedAndAttached() throws Exception {
        // mock new instances creation
        PowerMockito.whenNew(Configuration.class).withNoArguments().thenReturn(this.configuration);
        PowerMockito.whenNew(UserStore.class).withNoArguments().thenReturn(this.userStore);
        PowerMockito.whenNew(ImageStore.class).withNoArguments().thenReturn(this.imageStore);
        PowerMockito.whenNew(CarStore.class).withNoArguments().thenReturn(this.carStore);
        PowerMockito.whenNew(FrontItemFactory.class).withNoArguments().thenReturn(this.frontItemFactory);
        PowerMockito.whenNew(Gson.class).withNoArguments().thenReturn(this.gson);
        PowerMockito.whenNew(CarFactory.class).withNoArguments().thenReturn(this.carFactory);
        PowerMockito.whenNew(ImageFactory.class).withNoArguments().thenReturn(this.imageFactory);
        // configs for session factory
        when(this.configuration.configure()).thenReturn(this.configuration);
        when(this.configuration.buildSessionFactory()).thenReturn(this.sessionFactory);
        // run and verify
        new ContextListener().contextInitialized(this.sce);
        verify(this.ctx).setAttribute(Attributes.ATR_HB_FACTORY.v(), this.sessionFactory);
        verify(this.ctx).setAttribute(Attributes.ATR_USER_STORE.v(), this.userStore);
        verify(this.ctx).setAttribute(Attributes.ATR_IMAGE_STORE.v(), this.imageStore);
        verify(this.ctx).setAttribute(Attributes.ATR_CAR_STORE.v(), this.carStore);
        verify(this.ctx).setAttribute(Attributes.ATR_ITEM_FACTORY.v(), this.frontItemFactory);
        verify(this.ctx).setAttribute(Attributes.ATR_JSON_PARSER.v(), this.gson);
        verify(this.ctx).setAttribute(Attributes.ATR_CAR_FACTORY.v(), this.carFactory);
        verify(this.ctx).setAttribute(Attributes.ATR_IMAGE_FACTORY.v(), this.imageFactory);
    }

    @Test
    public void whenContextDestroyedThenResourceClosed() throws Exception {
        when(this.ctx.getAttribute(Attributes.ATR_HB_FACTORY.v())).thenReturn(this.sessionFactory);
        new ContextListener().contextDestroyed(this.sce);
        verify(this.sessionFactory).close();
    }
}