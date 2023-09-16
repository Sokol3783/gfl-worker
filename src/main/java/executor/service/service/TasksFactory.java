package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

import java.util.concurrent.Callable;

/**
 * Callable, Runnable Factory.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface TasksFactory<T> {

    Callable<T> createTaskWorker(T listener);

    Runnable createExecutionWorker(ExecutionService service,
                                   Scenario scenario,
                                   ProxyConfigHolder proxy);

}
