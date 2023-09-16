package executor.service.config;

import executor.service.config.properties.PropertiesConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PropertiesConfigTest {

    private PropertiesConfig propertiesConfig;

    @BeforeEach
    public void setUp() {
        propertiesConfig = new PropertiesConfig();
    }

    @Test
    public void testGetPropertiesValidFile() {
        Properties properties = propertiesConfig.getProperties("test.properties");

        assertNotNull(properties);
        assertEquals("value1", properties.getProperty("key1"));
        assertEquals("value2", properties.getProperty("key2"));
    }
}
