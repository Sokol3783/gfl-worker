package executor.service.service.parallelflowexecutor;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {
    private ThreadPoolExecutor executor;
    private List<Task> tasks;

    @Override
    public void submit(Thread thread) {

    }

    @Override
    public void execute(Thread thread) {

    }
}
