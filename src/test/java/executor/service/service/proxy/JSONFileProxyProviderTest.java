package executor.service.service.proxy;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.service.proxy.impl.JSONFileProxyProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONFileProxyProviderTest {

    private static final int EXPECTED_LIST_SIZE = 2;
    private ProxyProvider provider;
    private List<ProxyConfigHolder> proxies;

    @BeforeEach
    void setUp() {
        provider = new JSONFileProxyProvider();
        proxies = provider.readProxy();
    }

    @Test
    void testReadScenarios_count() {
        assertEquals(EXPECTED_LIST_SIZE, proxies.size());
    }

    @Test
    void testReadScenarios_type() {
        for(ProxyConfigHolder proxy: proxies){
            assertEquals(ProxyConfigHolder.class, proxy.getClass());
        }
    }

    @AfterEach
    void tearDown() {
        provider = null;
        proxies = null;
    }
}
