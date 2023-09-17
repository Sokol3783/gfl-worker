package executor.service.service;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

/**
 * The facade for execute ScenarioExecutor.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ExecutionService {

    void execute(Scenario scenario, ProxyConfigHolder proxy);
}
