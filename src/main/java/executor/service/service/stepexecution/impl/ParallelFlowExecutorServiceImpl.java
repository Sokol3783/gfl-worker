package executor.service.service.impl;

import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.Task;

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
