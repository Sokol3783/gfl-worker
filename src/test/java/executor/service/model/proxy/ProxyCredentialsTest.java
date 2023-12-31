package executor.service.model.proxy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProxyCredentialsTest {
    private ProxyCredentials proxyCredentials;

    @BeforeEach
    void setUp() {
        proxyCredentials = new ProxyCredentials("test name", "test password");
    }

    @AfterEach
    void tearDown() {
        proxyCredentials = null;
    }

    @Test
    void testGetUsername() {
        assertEquals("test name", proxyCredentials.getUsername());
    }

    @Test
    void testSetUsername() {
        proxyCredentials.setUsername("some name");
        assertEquals("some name", proxyCredentials.getUsername());
    }

    @Test
    void testGetPassword() {
        assertEquals("test password", proxyCredentials.getPassword());
    }

    @Test
    void testSetPassword() {
        proxyCredentials.setPassword("12345");
        assertEquals("12345", proxyCredentials.getPassword());
    }

    @Test
    void testEquals() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        proxyCredentials1.setUsername("test name");
        proxyCredentials1.setPassword("test password");
        assertEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    void testEqualsWithEmptyObjects() {
        proxyCredentials.setUsername(null);
        proxyCredentials.setPassword(null);
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        assertEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    void testNotEqualsByUsername() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        proxyCredentials1.setUsername("other name");
        assertNotEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    void testNotEqualsByPassword() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        proxyCredentials1.setPassword("wrong password");
        assertNotEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    void testHashCode() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials("test name", "test password");
        assertEquals(proxyCredentials.hashCode(), proxyCredentials1.hashCode());
    }

    @Test
    void testHashCodeNotMatch() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        assertNotEquals(proxyCredentials.hashCode(), proxyCredentials1.hashCode());
    }
}
