package executor.service.service.executionservice;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

public interface ExecutionService {
    void execute(Scenario scenario, ProxyConfigHolder proxy);
}
