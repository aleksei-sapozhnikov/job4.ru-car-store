package carstore.store;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class CarStoreXmlTest {

    private final CarStore store = CarStoreXml.getInstance();

    @AfterClass
    public static void closeStorage() throws Exception {
        CarStoreXml.getInstance().close();
    }

    @Before
    public void clearStorage() {
        ((CarStoreXml) this.store).clear();
    }

    @Test
    public void whenMergeNotStoredIdThenSavedWithNewIds() {
        CarStoreIntegralTestMethods
                .whenMergeNotStoredIdThenSavedWithNewIds(this.store);
    }

    @Test
    public void whenMergeStoredIdThenUpdatedWithSameIds() {
        CarStoreIntegralTestMethods
                .whenMergeStoredIdThenUpdatedWithSameIds(this.store);
    }

    @Test
    public void whenGetThenSameAsSaved() {
        CarStoreIntegralTestMethods
                .whenGetThenSameAsSaved(this.store);
    }

    @Test
    public void whenDeleteThenOutFromStorage() {
        CarStoreIntegralTestMethods
                .whenDeleteThenOutFromStorage(this.store);
    }

    @Test
    public void whenGetAllThenListOfSavedEntities() {
        CarStoreIntegralTestMethods
                .whenGetAllThenListOfSavedEntities(this.store);
    }
}