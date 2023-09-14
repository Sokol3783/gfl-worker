package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.RunnableFactory;
import executor.service.service.parallel.ExecutionWorker;
import executor.service.service.parallel.TaskWorker;

import java.util.concurrent.BlockingQueue;

/**
 * Thread factory implementation.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class RunnableFactoryImpl<T> implements RunnableFactory<T> {

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
