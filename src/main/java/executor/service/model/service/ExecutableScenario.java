package executor.service.model.service;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

public record ExecutableScenario(ProxyConfigHolder proxy, Scenario scenario) {
}
