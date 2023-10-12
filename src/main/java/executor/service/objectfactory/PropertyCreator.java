package executor.service.objectfactory;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.configs.ThreadPoolConfig;
import executor.service.model.configs.WebDriverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;

class PropertyCreator {

    private static final Logger log = LoggerFactory.getLogger(PropertyCreator.class);
    private static final String PATH_TO_THREAD_PROPERTIES = System.getProperty("thread-pool.properties");
    private static final String PATH_TO_WEBDRIVER_PROPERTIES = System.getProperty("web-driver.properties");

    static ThreadPoolConfig getThreadPoolConfig()  throws InstantiationException {
        try {
            ThreadPoolConfig pool = new ThreadPoolConfig();
            Properties properties = PropertiesConfig.getProperties(PATH_TO_THREAD_PROPERTIES);
            pool.setCorePoolSize(Integer.parseInt(properties.getProperty(CORE_POOL_SIZE, "2")));
            pool.setKeepAliveTime(Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME, "2")));
            pool.setTimeUnit(TimeUnit.valueOf(properties.getProperty(TIMEUNIT,"MILLISECONDS")));
            return pool;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InstantiationException("Set the environment variable for the path of thread pool properties!");
        }
    }

    static WebDriverConfig createWebDriverConfig() throws InstantiationException{
        try {
            WebDriverConfig driver = new WebDriverConfig();
            Properties properties = PropertiesConfig.getProperties(PATH_TO_WEBDRIVER_PROPERTIES);
            driver.setWebDriverExecutable(properties.getProperty("webDriver-executable", "\\chromedriver.exe"));
            driver.setUserAgent(properties.getProperty("user-agent","default"));
            driver.setImplicitlyWait(Long.valueOf(properties.getProperty("implicitly-wait","5000")));
            driver.setPageLoadTimeout(Long.valueOf(properties.getProperty("page-load-timeout","15000")));
            return driver;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InstantiationException("Set the environment variable for the path of thread pool properties!");
        }
    }

}
