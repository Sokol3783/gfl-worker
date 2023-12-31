package executor.service.model.proxy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProxyNetworkConfigTest {
    private static final String HOSTNAME = "example.com";
    private static final String HOSTNAME_ANOTHER = "example.org";
    private static final Integer PORT = 8080;
    private static final Integer PORT_ANOTHER = 8081;
    private ProxyNetworkConfig config1;
    private ProxyNetworkConfig config2;
    private ProxyNetworkConfig config3;

    @BeforeEach
    void setUp() {
        config1 = new ProxyNetworkConfig(HOSTNAME, PORT);
        config2 = new ProxyNetworkConfig(HOSTNAME, PORT);
        config3 = new ProxyNetworkConfig(HOSTNAME_ANOTHER, PORT_ANOTHER);
    }

    @AfterEach
    void tearDown() {
        config1 = null;
        config2 = null;
        config3 = null;
    }

    @Test
    void testEmptyConstructor() {
        ProxyNetworkConfig config = new ProxyNetworkConfig();

        assertNull(config.getHostname());
        assertNull(config.getPort());
    }

    @Test
    void testParameterizedConstructorAndSetters() {
        ProxyNetworkConfig config = new ProxyNetworkConfig();
        config.setHostname(HOSTNAME);
        config.setPort(PORT);

        assertEquals(config, config1);
    }

    @Test
    void testEquals() {
        assertEquals(config1, config2);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(config1, config3);
    }

    @Test
    void testHashCode() {
        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    void testHashCodeNotMatch() {
        assertNotEquals(config1.hashCode(), config3.hashCode());
    }
}
