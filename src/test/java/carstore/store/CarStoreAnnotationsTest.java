package carstore.store;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Before;

public class CarStoreAnnotationsTest {


    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();


    @AfterClass
    public static void closeStorage() throws Exception {
        FACTORY.close();
    }

    @Before
    public void clearStorage() {
        CarStoreIntegralTestMethods.performTransaction(FACTORY, session ->
                session.createQuery("delete from Car").executeUpdate());
    }

//    @Test
//    public void whenMergeNotStoredIdThenSavedWithNewIds() {
//        CarStoreIntegralTestMethods
//                .whenSaveNotStoredIdThenSavedWithNewIds(FACTORY);
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