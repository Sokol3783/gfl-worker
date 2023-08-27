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
    private final PropertiesConfig propertiesConfig;
    private final ThreadPoolConfig threadPoolConfig;
    private final ExecutorService threadPoolExecutor;

    public ParalleFlowExecutorService(ExecutionService service,
                                      PropertiesConfig propertiesConfig,
                                      ThreadPoolConfig threadPoolConfig) {
        this.service = service;
        this.propertiesConfig = propertiesConfig;
        this.threadPoolConfig = configureThreadPoolConfig(this.propertiesConfig, threadPoolConfig);
        this.threadPoolExecutor = createThreadPoolExecutor(this.threadPoolConfig);
    }

    /**
     * Adds array of user scripts to ParalleFlowExecutorService.
     */
    public void execute() {
        threadPoolExecutor.execute(service::execute);
    }

    /**
     * Shut down the ParallelFlowExecutorService.
     */
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    /**
     * Create ThreadPoolExecutor.
     *
     * @param threadPoolConfig the config for the ThreadPoolExecutor
     * @return the ThreadPoolExecutor entity
     */
    private ThreadPoolExecutor createThreadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                MAXIMUM_POOL_SIZE,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Configure ThreadPoolConfig from properties file.
     *
     * @param propertiesConfig the properties from resources file
     * @param threadPoolConfig the ThreadPoolConfig entity
     * @return configured thread pool config
     */
    private ThreadPoolConfig configureThreadPoolConfig(PropertiesConfig propertiesConfig, ThreadPoolConfig threadPoolConfig) {
        var properties = propertiesConfig.getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));
        threadPoolConfig.setCorePoolSize(corePoolSize);
        threadPoolConfig.setKeepAliveTime(keepAliveTime);

        return threadPoolConfig;
    }
}
