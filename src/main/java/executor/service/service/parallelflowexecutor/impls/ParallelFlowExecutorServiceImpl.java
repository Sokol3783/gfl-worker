package executor.service.service.parallelflowexecutor.impls;

import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.ContinuousOperations;

import java.util.concurrent.*;

public class ParallelFlowExecutorServiceImpl extends ThreadPoolExecutor implements ParallelFlowExecutorService {
    private final ContinuousOperations keeper;

    public ParallelFlowExecutorServiceImpl(int corePoolSize,
                                           int maximumPoolSize,
                                           long keepAliveTime,
                                           TimeUnit unit,
                                           ThreadFactory threadFactory,
                                           BlockingQueue<Runnable> workQueue,
                                           ContinuousOperations keeper) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.keeper = keeper;
    }


    @Override
    public Future<?> submit(Runnable task) {
        keepAliveTaskThreads();
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        keepAliveTaskThreads();
        return super.submit(task, result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        keepAliveTaskThreads();
        return super.submit(task);
    }

    private void keepAliveTaskThreads() {
        keeper.startInterruptedJob();
    }

    @Override
    public void execute(Runnable command) {
        keepAliveTaskThreads();
        super.execute(command);
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        throw new UnsupportedOperationException();
    }
}
