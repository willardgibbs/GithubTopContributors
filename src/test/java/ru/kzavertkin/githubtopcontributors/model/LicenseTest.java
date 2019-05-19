package ru.kzavertkin.githubtopcontributors.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class LicenseTest {
    @Autowired
    private JacksonTester<License> licenseJacksonTester;

    @Test
    public void serializationTest() throws IOException {
        License license = getLicense();
        assertThat(licenseJacksonTester.write(license)).isEqualToJson("license.json", License.class);
    }

    @Test
    public void deserializationTest() throws IOException {
        License license = getLicense();
        assertThat(licenseJacksonTester.readObject("license.json")).isEqualTo(license);
    }

    private License getLicense() {
        License license = new License();
        license.setKey("test");
        license.setName("test");
        license.setSpdxId("test");
        license.setUrl("test");
        license.setNodeId("test");
        return license;
    }

}