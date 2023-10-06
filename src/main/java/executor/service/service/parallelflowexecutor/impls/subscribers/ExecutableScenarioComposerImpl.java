package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;

import java.util.ArrayList;
import java.util.List;

public class ExecutableScenarioComposerImpl implements ExecutableScenarioComposer {
    
    private final ProxyQueue proxyQueue;
    private final ScenarioQueue scenarioQueue;
    private final ProxyConfigHolder defaultProxy;

    public ExecutableScenarioComposerImpl(ProxyQueue proxyQueue, ScenarioQueue scenarioQueue, ProxyConfigHolder defaultProxy) {
        this.proxyQueue = proxyQueue;
        this.scenarioQueue = scenarioQueue;
        this.defaultProxy = defaultProxy;
    }

    @Override
    public List<ExecutableScenario> composeExecutableScenarios() {        
        try {
            List<ProxyConfigHolder> proxies = proxyQueue.getAllProxy();
            List<Scenario> scenarios = scenarioQueue.getAllScenario();
            if (!scenarios.isEmpty() && !proxies.isEmpty()) {
                return composeNotEmptyList(proxies, scenarios);
            } else if (!scenarios.isEmpty()) {
                return composeByDefaultProxy(defaultProxy, scenarios);
            } else {
                proxies.forEach(proxyQueue::addProxy);
            }
        } catch (InterruptedException e) {
            
        }

        return new ArrayList<>();
    }

    private List<ExecutableScenario> composeByDefaultProxy(ProxyConfigHolder defaultProxy, List<Scenario> scenarios) {
        List<ExecutableScenario> executableScenarios = new ArrayList<>();
        scenarios.forEach(s -> executableScenarios.add(new ExecutableScenario(defaultProxy, s)));
        return executableScenarios;
    }

    private List<ExecutableScenario> composeNotEmptyList(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        List<ExecutableScenario> executableScenarios;
        if (proxies.size() == scenarios.size()) {
            executableScenarios = composeOneToOne(proxies, scenarios);
        } else {
            executableScenarios = composeMix(proxies, scenarios);
        }
        return executableScenarios;
    }

    private List<ExecutableScenario> composeMix(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        int maxSize = Math.max(scenarios.size(), proxies.size());
        List<ExecutableScenario> executableScenarios = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            ExecutableScenario executableScenario = new ExecutableScenario(
                    proxies.get(i % proxies.size()),
                    scenarios.get(i % scenarios.size())
            );
            executableScenarios.add(executableScenario);
        }

        return executableScenarios;
    }

    private List<ExecutableScenario> composeOneToOne(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        List<ExecutableScenario> executableScenarios = new ArrayList<>(proxies.size());

        for (int i = 0; i < proxies.size(); i++) {
            ExecutableScenario executableScenario = new ExecutableScenario(
                    proxies.get(i),
                    scenarios.get(i)
            );
            executableScenarios.add(executableScenario);
        }

        return executableScenarios;
    }
}

