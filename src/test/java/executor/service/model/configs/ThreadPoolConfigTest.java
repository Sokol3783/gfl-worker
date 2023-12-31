package executor.service.model.configs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class ThreadPoolConfigTest {

    private ThreadPoolConfig config1;
    private ThreadPoolConfig config2;
    private ThreadPoolConfig config3;
    private ThreadPoolConfig configNull;

    @BeforeEach
     void setUp() {
        config1 = new ThreadPoolConfig();
        config2 = new ThreadPoolConfig();
        config3 = new ThreadPoolConfig();
        configNull = null;
    }

    @AfterEach
     void tearDown() {
        config1 = null;
        config2 = null;
        config3 = null;
    }

    @Test
     void reflexiveTest() {
        assertNotNull(config1);
        assertEquals(config1, config1); //TODO
        assertNotEquals(config1, configNull);
    }

    @Test
     void symmetricTest() {
        assertNotNull(config1);
        assertNotNull(config2);
        assertEquals(config1.equals(config2),config2.equals(config1));
    }

    @Test
     void transitiveTest() {
        assertNotNull(config1);
        assertNotNull(config2);
        assertNotNull(config3);
        assertEquals(config1.equals(config2),config2.equals(config3));
    }

    @Test
     void equalsWithNull() {
        assertNotNull(config1);
        assertNotEquals(null, config1);
    }

    @Test
     void emptyConstructorTest() {
        ThreadPoolConfig emptyConfig = new ThreadPoolConfig();

        assertNotNull(emptyConfig);
    }

    @Test
     void hashCodeTest() {
        config1.setCorePoolSize(10);
        config1.setKeepAliveTime(100L);
        config2.setCorePoolSize(10);
        config2.setKeepAliveTime(100L);

        assertEquals(config1.hashCode(),config2.hashCode());
    }

    @Test
     void allArgsConstructorTest() {
        config1 = new ThreadPoolConfig(10,100L);
        config2 = new ThreadPoolConfig(10,100L);

        assertEquals(config1, config2);
    }

    @Test
     void setTest() {
        config1.setCorePoolSize(10);
        config1.setKeepAliveTime(100L);

        assertEquals(10, (int) config1.getCorePoolSize());
        assertEquals(100L, (long) config1.getKeepAliveTime());
    }

    @Test
     void getTest() {
        config1 = new ThreadPoolConfig(10,100L);

        assertEquals(10, (int) config1.getCorePoolSize());
        assertEquals(100L, (long) config1.getKeepAliveTime());
    }
}
