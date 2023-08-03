package executor.service.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ProxyNetworkConfigTest {
    private static final String HOSTNAME = "example.com";
    private static final String HOSTNAME_ANOTHER = "example.org";
    private static final Integer PORT = 8080;
    private static final Integer PORT_ANOTHER = 8081;
    private ProxyNetworkConfig config1;
    private ProxyNetworkConfig config2;
    private ProxyNetworkConfig config3;

    @Before
    public void setUp() {
        config1 = new ProxyNetworkConfig(HOSTNAME, PORT);
        config2 = new ProxyNetworkConfig(HOSTNAME, PORT);
        config3 = new ProxyNetworkConfig(HOSTNAME_ANOTHER, PORT_ANOTHER);
    }

    @After
    public void tearDown() {
        config1 = null;
        config2 = null;
        config3 = null;
    }

    @Test
    public void testEmptyConstructor() {
        ProxyNetworkConfig config = new ProxyNetworkConfig();
        assertNull(config.getHostname());
        assertNull(config.getPort());
    }

    @Test
    public void testParameterizedConstructorAndSetters() {
        ProxyNetworkConfig config = new ProxyNetworkConfig();
        config.setHostname(HOSTNAME);
        config.setPort(PORT);
        assertEquals(config, config1);
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