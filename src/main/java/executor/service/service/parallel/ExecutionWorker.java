package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;

/**
 * Execution task.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ExecutionWorker implements Runnable {

    private final ExecutionService service;
    private final Scenario scenario;
    private final ProxyConfigHolder proxy;

    public ExecutionWorker(ExecutionService service,
                           Scenario scenario,
                           ProxyConfigHolder proxy) {
        this.service = service;
        this.scenario = scenario;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        service.execute(scenario, proxy);
    }
}
