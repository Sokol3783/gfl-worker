package executor.service.service;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ParalleFlowExecutorService {

    private static final Logger log = LoggerFactory.getLogger(ParalleFlowExecutorService.class);
    private static final int MAXIMUM_POOL_SIZE = 16;

    private final ScenarioExecutor scenarioExecutor;
    private final ThreadPoolExecutor threadPool;

    public ParalleFlowExecutorService(ScenarioExecutor scenarioExecutor) {
        this.scenarioExecutor = scenarioExecutor;
        threadPool = createThreadPoolExecutor();
    }

    /**
     * Adds Scenario to ParalleFlowExecutorService.
     *
     * @param scenario  the scenario for execution in parallel flow
     * @param webDriver the remote control interface that enables introspection and control of user agents (browsers)
     */
    public void enqueueScenarioForExecution(Scenario scenario, WebDriver webDriver) {
        threadPool.execute(() -> execute(scenario, webDriver));
    }

    /**
     * Shut down the ParallelFlowExecutorService.
     */
    public void shutdown() {
        threadPool.shutdown();
    }

    /**
     * Handles and logs errors.
     *
     * @param scenario  the scenario for execution in parallel flow
     * @param webDriver the remote control interface that enables introspection and control of user agents (browsers)
     */
    private void execute(Scenario scenario, WebDriver webDriver) {
        try {
            scenarioExecutor.execute(scenario, webDriver);
        } catch (Exception e) {
            log.error("An error occurred during scenario execution", e);
        }
    }

    /**
     * Create ThreadPoolExecutor from ThreadPoolConfig.class.
     */
    private ThreadPoolExecutor createThreadPoolExecutor() {
        ThreadPoolConfig threadPoolConfig = createThreadPoolConfig();
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                MAXIMUM_POOL_SIZE,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Create ThreadPoolConfig from properties file.
     */
    private ThreadPoolConfig createThreadPoolConfig() {
        var properties = new PropertiesConfig().getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));

        return new ThreadPoolConfig(corePoolSize, keepAliveTime);
    }
}
