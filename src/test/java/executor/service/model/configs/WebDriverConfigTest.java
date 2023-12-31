package executor.service.model.configs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WebDriverConfigTest {

    private WebDriverConfig config1;
    private WebDriverConfig config2;
    private WebDriverConfig config3;

    @BeforeEach
    void setUp() {
        config1 = new WebDriverConfig("driver.exe", "Chrome", 5000L, 2000L);
        config2 = new WebDriverConfig("driver.exe", "Chrome", 5000L, 2000L);
        config3 = new WebDriverConfig("geckodriver.exe", "Firefox", 3000L, 1000L);
    }

    @AfterEach
    void tearDown() {
        config1 = null;
        config2 = null;
        config3 = null;
    }

    @Test
    void testGettersAndSetters() {
        config1.setWebDriverExecutable("chromedriver.exe");
        config1.setUserAgent("Chrome");
        config1.setPageLoadTimeout(6000L);
        config1.setImplicitlyWait(1500L);

        assertEquals("chromedriver.exe", config1.getWebDriverExecutable());
        assertEquals("Chrome", config1.getUserAgent());
        assertEquals(6000L, (long) config1.getPageLoadTimeout());
        assertEquals(1500L, (long) config1.getImplicitlyWait());
    }

    @Test
    void testEquals() {
        assertEquals(config1, config1);
        assertEquals(config1, config2);
        assertEquals(config2, config1);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(config1, config3);
        assertNotEquals(config2, config3);
    }

    @Test
    void testHashCode() {
        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    void testHashCodeNotMatch() {
        assertNotEquals(config1.hashCode(), config3.hashCode());
        assertNotEquals(config2.hashCode(), config3.hashCode());
    }
}
