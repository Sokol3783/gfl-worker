package executor.service.service;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ThreadPoolConfig;

import java.util.concurrent.ExecutorService;
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

    private static final int MAXIMUM_POOL_SIZE = 16;

    private final ExecutionService service;
    private final ExecutorService threadPool;

    public ParalleFlowExecutorService(ExecutionService service) {
        this.service = service;
        this.threadPool = createThreadPoolExecutor();
    }

    /**
     * Adds array of user scripts to ParalleFlowExecutorService.
     */
    public void execute() {
        threadPool.execute(service::execute);
    }

    /**
     * Shut down the ParallelFlowExecutorService.
     */
    public void shutdown() {
        threadPool.shutdown();
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
