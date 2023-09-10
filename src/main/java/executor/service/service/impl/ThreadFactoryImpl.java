package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ThreadFactory;
import executor.service.service.parallel.ExecutionWorker;
import executor.service.service.parallel.TaskWorker;

import java.util.concurrent.BlockingQueue;

public class ThreadFactoryImpl<T> implements ThreadFactory<T> {

    @Override
    public Runnable createTaskWorker(T listener, BlockingQueue<T> items) {
        return new TaskWorker<>(listener, items);
    }

    @Override
    public Runnable createExecutionWorker(ExecutionService service,
                                          Scenario scenario,
                                          ProxyConfigHolder proxy) {
        return new ExecutionWorker(service, scenario, proxy);
    }
}
