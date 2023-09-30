package executor.service.objectfactory;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.configs.ThreadPoolConfig;
import executor.service.model.configs.WebDriverConfig;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;

class PropertyCreator {

    private static final String PATH_TO_THREAD_PROPERTIES = "thread-pool.properties";
    private static final String PATH_TO_WEBDRIVER_PROPERTIES = "web-driver.properties";

    static ThreadPoolConfig getThreadPoolConfig() {
        ThreadPoolConfig pool = new ThreadPoolConfig();
        try {
            Properties properties = PropertiesConfig.getProperties(PATH_TO_THREAD_PROPERTIES);
            pool.setCorePoolSize(Integer.parseInt(properties.getProperty(CORE_POOL_SIZE, "2")));
            pool.setKeepAliveTime(Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME, "2")));
            pool.setTimeUnit(TimeUnit.valueOf(properties.getProperty(TIMEUNIT,"MILLISECONDS")));
        } catch (Exception e) {
            pool =   new ThreadPoolConfig(2, 100L, TimeUnit.MILLISECONDS);
        }
        return pool;
    }

    static WebDriverConfig createWebDriverConfig(){
        WebDriverConfig driver = new WebDriverConfig();
        try {
            Properties properties = PropertiesConfig.getProperties(PATH_TO_WEBDRIVER_PROPERTIES);
            driver.setWebDriverExecutable(properties.getProperty("webDriver-executable", "\\chromedriver.exe"));
            driver.setUserAgent(properties.getProperty("user-agent","default"));
            driver.setImplicitlyWait(Long.valueOf(properties.getProperty("implicitly-wait","5000")));
            driver.setPageLoadTimeout(Long.valueOf(properties.getProperty("page-load-timeout","15000")));
        } catch (Exception e) {
            driver = new WebDriverConfig("\\chromedriver.exe","default",5000L, 15000L);
        }
        return driver;
    }

}
