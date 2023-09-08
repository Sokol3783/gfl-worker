package executor.service.config.properties;

/**
 * Class for application`s constants.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * */
public final class PropertiesConstants {

    private PropertiesConstants() {
        throw new AssertionError("non-instantiable class");
    }

    public static final String THREAD_POOL_PROPERTIES = "thread-pool.properties";
    public static final String CORE_POOL_SIZE = "core.pool.size";
    public static final String KEEP_ALIVE_TIME = "keep.alive.time";
    public static final String COMMONS_CONFIGURATION_PROPERTIES = "web-driver.properties";
    public static final String PROXY_VALIDATOR_TARGET_URL = "target.url";
    public static final Integer PROXY_VALIDATOR_CONNECTION_TIMEOUT = Integer.getInteger("connection.timeout");


}
