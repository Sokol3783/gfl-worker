package executor.service.model;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.proxy.ProxyCredentials;
import executor.service.model.proxy.ProxyNetworkConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyConfigHolderTest {

    private static final String USERNAME = "user";
    private static final String USERNAME_ANOTHER = "user1";
    private static final String PASSWORD = "admin";
    private static final String LOCALHOST = "localhost";
    private static final String LOCALHOST_ANOTHER = "localhost1";
    private static final Integer PORT = 5080;
    private static final Integer PORT_ANOTHER = 7060;

    private ProxyCredentials proxyCredentials1;
    private ProxyCredentials proxyCredentials2;
    private ProxyNetworkConfig proxyNetworkConfig1;
    private ProxyNetworkConfig proxyNetworkConfig2;
    private ProxyConfigHolder proxyConfigHolder1;
    private ProxyConfigHolder proxyConfigHolder2;

    @BeforeEach
    public void setUp() {
        proxyCredentials1 = new ProxyCredentials(USERNAME, PASSWORD);
        proxyCredentials2 = new ProxyCredentials(USERNAME_ANOTHER, PASSWORD);
        proxyNetworkConfig1 = new ProxyNetworkConfig(LOCALHOST, PORT);
        proxyNetworkConfig2 = new ProxyNetworkConfig(LOCALHOST_ANOTHER, PORT_ANOTHER);
        proxyConfigHolder1 = new ProxyConfigHolder(proxyNetworkConfig1, proxyCredentials1);
        proxyConfigHolder2 = new ProxyConfigHolder(proxyNetworkConfig2, proxyCredentials2);
    }

    @AfterEach
    public void tearDown() {
        proxyCredentials1 = null;
        proxyCredentials2 = null;
        proxyNetworkConfig1 = null;
        proxyNetworkConfig2 = null;
        proxyConfigHolder1 = null;
        proxyConfigHolder2 = null;
    }

    @Test
    public void testNoArgsConstructor() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();

        assertNull(proxyConfigHolder.getProxyCredentials());
        assertNull(proxyConfigHolder.getProxyNetworkConfig());
    }

    @Test
    public void testAllArgsConstructor() {
        assertEquals(proxyConfigHolder1.getProxyCredentials(), proxyCredentials1);
        assertEquals(proxyConfigHolder1.getProxyNetworkConfig(), proxyNetworkConfig1);
    }

    @Test
    public void testEquals() {
        proxyConfigHolder2 = new ProxyConfigHolder(proxyNetworkConfig1, proxyCredentials1);

        assertEquals(proxyConfigHolder1, proxyConfigHolder2);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(proxyConfigHolder1, proxyConfigHolder2);
    }

    @Test
    public void testHashCodeAndSetters() {
        //reassign differing params from proxyConfigHolder1 to proxyNetworkConfig2
        proxyConfigHolder2.setProxyCredentials(proxyConfigHolder1.getProxyCredentials());
        proxyConfigHolder2.setProxyNetworkConfig(proxyConfigHolder1.getProxyNetworkConfig());

        assertEquals(proxyConfigHolder1.hashCode(), proxyConfigHolder2.hashCode());
    }

    @Test
    public void testHashCodeNotMatch() {
        assertNotEquals(proxyConfigHolder1.hashCode(), proxyConfigHolder2.hashCode());
    }
}
