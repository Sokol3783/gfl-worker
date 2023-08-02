package executor.service.model;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class ThreadPoolConfigTest {

    ThreadPoolConfig config1;
    ThreadPoolConfig config2;
    ThreadPoolConfig config3;

    ThreadPoolConfig configNull;

    @Before
    public void setUp() {
        config1 = new ThreadPoolConfig();
        config2 = new ThreadPoolConfig();
        config3 = new ThreadPoolConfig();
        configNull = null;
    }

    @Test
    public void reflexiveTest() {
        assertNotNull(config1);
        assertTrue(config1.equals(config1));
        assertFalse(config1.equals(configNull));
    }

    @Test
    public void symmetricTest() {
        assertNotNull(config1);
        assertNotNull(config2);
        assertEquals(config1.equals(config2),config2.equals(config1));
    }

    @Test
    public void transitiveTest() {
        assertNotNull(config1);
        assertNotNull(config2);
        assertNotNull(config3);
        assertEquals(config1.equals(config2),config2.equals(config3));
    }

    @Test
    public void equalsWithNull() {
        assertNotNull(config1);
        assertFalse(config1.equals(null));
    }

    @Test
    public void emptyConstructorTest() {
        ThreadPoolConfig emptyConfig = new ThreadPoolConfig();
        assertNotNull(emptyConfig);
    }
    @Test
    public void hashCodeTest() {
        config1.setCorePoolSize(10);
        config1.setKeepAliveTime(100L);
        config2.setCorePoolSize(10);
        config2.setKeepAliveTime(100L);
        assertEquals(config1.hashCode(),config2.hashCode());
    }

    @Test
    public void AllArgsConstructorTest() {
        config1 = new ThreadPoolConfig(10,100L);
        config2 = new ThreadPoolConfig(10,100L);
        assertTrue(config1.equals(config2));
    }
}