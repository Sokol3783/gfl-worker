package executor.service.config.properties;

/**
 * Application`s constants.
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

    public static final String TIMEUNIT = "timeunit";
    public static final String COMMONS_CONFIGURATION_PROPERTIES = "web-driver.properties";
    public static final String PROXY_VALIDATOR_TARGET_URL = "target.url";
    public static final Integer PROXY_VALIDATOR_CONNECTION_TIMEOUT = Integer.parseInt("connection.timeout");
    public static final String WEB_DRIVER = "web-driver.properties";
    public static final String WEB_DRIVER_EXECUTABLE = "webDriver-executable";
    public static final String CHROME_EXECUTABLE = "chrome-executable";
    public static final String CHROME_VERSION = "chrome-version";
    public static final String USER_AGENT = "user-agent";
    public static final String PAGE_LOAD_TIMEOUT = "page-load-timeout";
    public static final String IMPLICITLY_WAIT = "implicitly-wait";
}
