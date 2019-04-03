package carstore.util;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertThat;


public class PropertiesHolderTest {

    public static final String SAMPLE_RESOURCE_REFERENCE = "carstore/util/sampleResource";

    public PropertiesHolderTest() throws IOException, URISyntaxException {
        this.writeSampleResource();
    }

    @Test
    public void whenCreatedThenReadResourceAndReturnValuesByGetters() {
        var holder = new PropertiesHolder(SAMPLE_RESOURCE_REFERENCE);
        assertThat(holder.getManufacturer(), Matchers.containsInAnyOrder("Mercedes", "Ford"));
        assertThat(holder.getNewness(), Matchers.containsInAnyOrder("new", "used", "old"));
        assertThat(holder.getBodyType(), Matchers.containsInAnyOrder("sedan", "pickup", "truck"));
        assertThat(holder.getColor(), Matchers.containsInAnyOrder("white", "black", "red"));
        assertThat(holder.getEngineFuel(), Matchers.containsInAnyOrder("gasoline", "kerosene"));
        assertThat(holder.getTransmissionType(), Matchers.containsInAnyOrder("automatic", "manual"));
    }

    private void writeSampleResource() throws URISyntaxException, IOException {
        var resourcePath = Path.of(this.getClass().getClassLoader().getResource(SAMPLE_RESOURCE_REFERENCE).toURI());
        var values = String.join(System.lineSeparator(),
                "manufacturer.value1=Mercedes",
                "manufacturer.value2=Ford",

                "newness.value1=new",
                "newness.value2=used",
                "newness.value3=old",

                "bodyType.value1 = sedan",
                "bodyType.value2 = pickup",
                "bodyType.value3 = truck",

                "color.value1 = white",
                "color.value2 = black",
                "color.value3 = red",

                "engineFuel.value1 = gasoline",
                "engineFuel.value2 = kerosene",

                "transmissionType.value1 = automatic",
                "transmissionType.value3 = manual"
        );
        Files.writeString(resourcePath, values, StandardOpenOption.TRUNCATE_EXISTING);
    }
}