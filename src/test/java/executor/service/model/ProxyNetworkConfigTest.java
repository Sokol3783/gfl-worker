package executor.service.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyNetworkConfigTest {
    private static final String HOSTNAME = "example.com";
    private static final Integer PORT = 8080;
    private static final ProxyNetworkConfig CONFIG1;
    private static final ProxyNetworkConfig CONFIG2;
    private static final ProxyNetworkConfig CONFIG3;

    static {
        CONFIG1 = new ProxyNetworkConfig("example.com", 8080);
        CONFIG2 = new ProxyNetworkConfig("example.com", 8080);
        CONFIG3 = new ProxyNetworkConfig("example.org", 8081);
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
        assertEquals(CONFIG1, CONFIG2);
        assertNotEquals(CONFIG1, CONFIG3);
    }

    @Test
    public void testHashCode() {
        assertEquals(CONFIG1.hashCode(), CONFIG2.hashCode());
        assertNotEquals(CONFIG1.hashCode(), CONFIG3.hashCode());
    }
}