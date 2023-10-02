package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

public class Pair {
    private final ProxyConfigHolder proxy;
    private final Scenario scenario;

    public Pair(ProxyConfigHolder proxy, Scenario scenario) {
        this.proxy = proxy;
        this.scenario = scenario;
    }

    public ProxyConfigHolder getProxy() {
        return proxy;
    }

    public Scenario getScenario() {
        return scenario;
    }
}
