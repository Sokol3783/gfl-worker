package executor.service.service.parallelflowexecutor.impls;

import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.TaskKeeper;

import java.util.concurrent.*;

public class ParallelFlowExecutorServiceImpl extends ThreadPoolExecutor implements ParallelFlowExecutorService {
    TaskKeeper keeper;

    public ParallelFlowExecutorServiceImpl(int corePoolSize,
                                           int maximumPoolSize,
                                           long keepAliveTime,
                                           TimeUnit unit,
                                           ThreadFactory threadFactory,
                                           BlockingQueue<Runnable> workQueue,
                                           TaskKeeper keeper) {
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
        if (keeper.taskNotAlive()) {
            keeper.nodes().stream().filter(s -> s.getThread() == null ||
                            s.getThread().isAlive()).
                    forEach(s -> s.setThread(super.getThreadFactory()
                            .newThread(s.getTask()))
                    );

        }
    }

    @Override
    public void execute(Runnable command) {
        keepAliveTaskThreads();
        super.execute(command);
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        throw  new UnsupportedOperationException();
    }
}
