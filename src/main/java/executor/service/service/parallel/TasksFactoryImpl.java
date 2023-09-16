package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.TasksFactory;

import java.util.concurrent.Callable;

/**
 * Callable, Runnable Factory.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class TasksFactoryImpl<T> implements TasksFactory<T> {

    @Override
    public Callable<T> createTaskWorker(T listener) {
        return new TaskWorker<>(listener);
    }

    @Override
    public Runnable createExecutionWorker(ExecutionService service,
                                          Scenario scenario,
                                          ProxyConfigHolder proxy) {
        return new ExecutionWorker(service, scenario, proxy);
    }
}
