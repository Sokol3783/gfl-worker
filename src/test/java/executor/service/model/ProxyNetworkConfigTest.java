package executor.service.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyNetworkConfigTest {

    @Test
    public void testEmptyConstructor() {
        ProxyNetworkConfig config = new ProxyNetworkConfig();
        assertNull(config.getHostname());
        assertNull(config.getPort());
    }

    @Test
    public void testParameterizedConstructor() {
        String hostname = "example.com";
        Integer port = 8080;
        ProxyNetworkConfig config = new ProxyNetworkConfig(hostname, port);
        assertEquals(hostname, config.getHostname());
        assertEquals(port, config.getPort());
    }

    @Test
    public void testGettersAndSetters() {
        ProxyNetworkConfig config = new ProxyNetworkConfig();
        String hostname = "proxy.example.com";
        Integer port = 8888;
        config.setHostname(hostname);
        config.setPort(port);

        assertEquals(hostname, config.getHostname());
        assertEquals(port, config.getPort());
    }

    @Test
    public void testEqualsAndHashCode() {
        ProxyNetworkConfig config1 = new ProxyNetworkConfig("example.com", 8080);
        ProxyNetworkConfig config2 = new ProxyNetworkConfig("example.com", 8080);
        ProxyNetworkConfig config3 = new ProxyNetworkConfig("example.org", 8081);

        assertEquals(config1, config2);
        assertNotEquals(config1, config3);
        assertEquals(config1.hashCode(), config2.hashCode());
        assertNotEquals(config1.hashCode(), config3.hashCode());
    }
}