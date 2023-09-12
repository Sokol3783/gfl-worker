package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

import java.util.concurrent.BlockingQueue;

/**
 * Thread factory.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ThreadFactory<T> {

    Runnable createTaskWorker(T listener, BlockingQueue<T> items);

    Runnable createExecutionWorker(ExecutionService service,
                                   Scenario scenario,
                                   ProxyConfigHolder proxy);

}
