package executor.service.config.property;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * Class for reading properties from properties file.
 * */
public class PropertiesConfig {

    /**
     * Get the properties from resources file.
     *
     * @param fileName properties file name with extension
     * */
    public Properties getProperties(String fileName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
