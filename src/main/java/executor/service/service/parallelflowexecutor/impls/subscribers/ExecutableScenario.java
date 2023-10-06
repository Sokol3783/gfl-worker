package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

public record ExecutableScenario(ProxyConfigHolder proxy, Scenario scenario) {
}
