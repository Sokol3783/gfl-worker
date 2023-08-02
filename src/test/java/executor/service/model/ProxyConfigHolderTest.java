package executor.service.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyConfigHolderTest {
    @Test
    public void testNoArgsConstructor() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();

        assertNull(proxyConfigHolder.getProxyCredentials());
        assertNull(proxyConfigHolder.getProxyNetworkConfig());
    }

    @Test
    public void testAllArgsConstructor() {
        ProxyCredentials proxyCredentials = new ProxyCredentials("user", "admin");
        ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("localhost", 5080);

        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);

        assertEquals(proxyConfigHolder.getProxyCredentials(), proxyCredentials);
        assertEquals(proxyConfigHolder.getProxyNetworkConfig(), proxyNetworkConfig);
    }

    @Test
    public void testEquals() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials("user1", "admin");
        ProxyNetworkConfig proxyNetworkConfig1 = new ProxyNetworkConfig("localhost1", 5080);
        ProxyConfigHolder proxyConfigHolder1 = new ProxyConfigHolder(proxyNetworkConfig1, proxyCredentials1);

        ProxyCredentials proxyCredentials2 = new ProxyCredentials("user2", "admin");
        ProxyNetworkConfig proxyNetworkConfig2 = new ProxyNetworkConfig("localhost2", 7060);
        ProxyConfigHolder proxyConfigHolder2 = new ProxyConfigHolder(proxyNetworkConfig2, proxyCredentials2);

        assertNotEquals(proxyConfigHolder1, proxyConfigHolder2);

        //assign new object for proxyNetworkConfig2 off of the params for proxyConfigHolder1
        proxyConfigHolder2 = new ProxyConfigHolder(proxyNetworkConfig1, proxyCredentials1);

        assertEquals(proxyConfigHolder1, proxyConfigHolder2);
    }

    @Test
    public void testHashCode() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials("user1", "admin");
        ProxyNetworkConfig proxyNetworkConfig1 = new ProxyNetworkConfig("localhost", 5080);
        ProxyConfigHolder proxyConfigHolder1 = new ProxyConfigHolder(proxyNetworkConfig1, proxyCredentials1);

        ProxyCredentials proxyCredentials2 = new ProxyCredentials("user2", "admin");
        ProxyNetworkConfig proxyNetworkConfig2 = new ProxyNetworkConfig("localhost", 5080);
        ProxyConfigHolder proxyConfigHolder2 = new ProxyConfigHolder(proxyNetworkConfig2, proxyCredentials2);

        assertNotEquals(proxyConfigHolder1.hashCode(), proxyConfigHolder2.hashCode());

        //reassign differing params from proxyConfigHolder1 to proxyNetworkConfig2
        proxyConfigHolder2.setProxyCredentials(proxyConfigHolder1.getProxyCredentials());

        assertEquals(proxyConfigHolder1.hashCode(), proxyConfigHolder2.hashCode());
    }
}
