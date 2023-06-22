package parkingSystem.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConfigTest {

    static Path path = Path.of(System.getProperty("user.dir"), "config.cfg");

    @BeforeAll
    static void createConfigFile() {
        assertDoesNotThrow(() -> {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()));
            bw.append("\r\nprop01 : 10" +
                    "\r\nprop02 : xyz" +
                    "\r\nprop03 : 15/06/2023");
            bw.close();
        });
    }

    @AfterAll
    static void deleteConfigFile() {
        path.toFile().delete();
    }

    @Test
    void shoudReadConfigFile() {
        Config config = new Config();
        assertDoesNotThrow(()-> config.readFromFile(path));

        String expected = "10";
        String actual = config.getPropertyValue("prop01");
        assertEquals(expected, actual);

        expected = "xyz";
        actual = config.getPropertyValue("prop02");
        assertEquals(expected, actual);

        expected = "15/06/2023";
        actual = config.getPropertyValue("prop03");
        assertEquals(expected, actual);
    }
}
