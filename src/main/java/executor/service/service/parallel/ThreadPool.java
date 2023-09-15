package executor.service.service.parallel;

import executor.service.model.ThreadPoolConfig;

import java.util.concurrent.ExecutorService;
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

    private static final int MAXIMUM_POOL_SIZE = 5;

    private final ThreadPoolConfig threadPoolConfig;

    public ThreadPool(ThreadPoolConfig threadPoolConfig) {
        this.threadPoolConfig = threadPoolConfig;
    }

    /**
     * Create ThreadPoolExecutor.
     *
     * @return ThreadPoolExecutor entity
     */
    public ExecutorService createThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                MAXIMUM_POOL_SIZE,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }
}
