package executor.service.model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class WebDriverConfigTest {

    private WebDriverConfig config1;
    private WebDriverConfig config2;
    private WebDriverConfig config3;

    @Before
    public void setUp() {
        config1 = new WebDriverConfig("driver.exe", "Chrome", 5000L, 2000L);
        config2 = new WebDriverConfig("driver.exe", "Chrome", 5000L, 2000L);
        config3 = new WebDriverConfig("geckodriver.exe", "Firefox", 3000L, 1000L);
    }
    @Test
    public void testGettersAndSetters() {
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
    public void testEquals() {

        assertEquals(config1, config1);


        assertEquals(config1, config2);
        assertEquals(config2, config1);

        assertNotEquals(config1, config3);
        assertNotEquals(config2, config3);
    }

    @Test
    public void testHashCode() {
        assertEquals(config1.hashCode(), config2.hashCode());

        assertNotEquals(config1.hashCode(), config3.hashCode());
        assertNotEquals(config2.hashCode(), config3.hashCode());
    }
}