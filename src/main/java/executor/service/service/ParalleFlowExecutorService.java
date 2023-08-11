package executor.service.service;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ThreadPoolConfig;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * Class for running ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ParalleFlowExecutorService {

    private final WebDriver webDriver;
    private final ExecutionService executionService;
    private final ThreadPoolExecutor threadPool;
    private final BlockingQueue<Runnable> blockingQueue;

    public ParalleFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
        blockingQueue = new LinkedBlockingQueue<>();
        threadPool = createThreadPoolExecutor();
    }

    /**
     * Method to add the Scenario in the ParalleFlowExecutorService().
     * */
    public void run() {

        threadPool.shutdown();

    }

    private ThreadPoolExecutor createThreadPoolExecutor() {
        ThreadPoolConfig threadPoolConfig = createThreadPoolConfig();
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                16,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.MINUTES,
                blockingQueue);
    }

    /**
     * Method to create the ThreadPoolConfig from properties file.
     * */
    private ThreadPoolConfig createThreadPoolConfig() {
        var properties = new PropertiesConfig().getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));

        return new ThreadPoolConfig(corePoolSize, keepAliveTime);
    }
}
