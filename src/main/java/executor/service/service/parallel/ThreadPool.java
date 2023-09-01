package executor.service.service.parallel;

import executor.service.model.ThreadPoolConfig;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create ThreadPoolExecutor.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ThreadPool {

    private final ThreadPoolConfig threadPoolConfig;

    public ThreadPool(ThreadPoolConfig threadPoolConfig) {
        this.threadPoolConfig = threadPoolConfig;
    }

    /**
     * Create ThreadPoolExecutor.
     *
     * @return ThreadPoolExecutor entity
     */
    public ThreadPoolExecutor createThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                defineMaximumAvailableProcessors(),
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }


    /**
     * Get the number of available processor cores.
     *
     * @return the number of available processor core
     */
    private int defineMaximumAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}
