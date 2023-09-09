package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.impl.ExecutionService;

import java.util.concurrent.BlockingQueue;

public interface ThreadFactory<T> {

    Runnable createTaskWorker(T listener, BlockingQueue<T> items);

    Runnable createExecutionWorker(ExecutionService service,
                                   Scenario scenario,
                                   ProxyConfigHolder proxy);

}
