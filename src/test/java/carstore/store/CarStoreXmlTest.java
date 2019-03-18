package carstore.store;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;

public class CarStoreXmlTest {

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    @AfterClass
    public static void closeStorage() throws Exception {
        FACTORY.close();
    }

//    @Before
//    public void clearStorage() {
//        ((CarStoreXml) this.store).clear();
//    }

//    @Test
//    public void whenSaveNotStoredIdThenSavedWithNewIds() {
//        CarStoreIntegralTestMethods
//                .whenSaveNotStoredIdThenSavedWithNewIds(this.FACTORY);
//    }

//    @Test
//    public void whenMergeStoredIdThenUpdatedWithSameIds() {
//        CarStoreIntegralTestMethods
//                .whenMergeStoredIdThenUpdatedWithSameIds(this.store);
//    }
//
//    @Test
//    public void whenGetThenSameAsSaved() {
//        CarStoreIntegralTestMethods
//                .whenGetThenSameAsSaved(this.store);
//    }
//
//    @Test
//    public void whenDeleteThenOutFromStorage() {
//        CarStoreIntegralTestMethods
//                .whenDeleteThenOutFromStorage(this.store);
//    }
//
//    @Test
//    public void whenGetAllThenListOfSavedEntities() {
//        CarStoreIntegralTestMethods
//                .whenGetAllThenListOfSavedEntities(this.store);
//    }
}