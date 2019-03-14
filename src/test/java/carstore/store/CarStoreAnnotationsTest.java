package carstore.store;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class CarStoreAnnotationsTest {

    private final CarStore store = CarStoreAnnotations.getInstance();

    @AfterClass
    public static void closeStorage() throws Exception {
        CarStoreAnnotations.getInstance().close();
    }

    @Before
    public void clearStorage() {
        ((CarStoreAnnotations) this.store).clear();
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