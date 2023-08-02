package executor.service.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyNetworkConfigTest {
    private static final String HOSTNAME = "example.com";
    private static final Integer PORT = 8080;
    private ProxyNetworkConfig config1;
    private ProxyNetworkConfig config2;
    private ProxyNetworkConfig config3;

    @Before
    public void setUp() {
        config1 = new ProxyNetworkConfig("example.com", 8080);
        config2 = new ProxyNetworkConfig("example.com", 8080);
        config3 = new ProxyNetworkConfig("example.org", 8081);
    }

    @Test
    public void testEmptyConstructor() {
        ProxyNetworkConfig config = new ProxyNetworkConfig();
        assertNull(config.getHostname());
        assertNull(config.getPort());
    }

    @Test
    public void testParameterizedConstructor() {
        ProxyNetworkConfig config = new ProxyNetworkConfig(HOSTNAME, PORT);
        assertEquals(HOSTNAME, config.getHostname());
        assertEquals(PORT, config.getPort());
    }

    @Test
    public void testEquals() {
        assertEquals(config1, config2);
        assertNotEquals(config1, config3);
    }

    @Test
    public void testHashCode() {
        assertEquals(config1.hashCode(), config2.hashCode());
        assertNotEquals(config1.hashCode(), config3.hashCode());
    }
}