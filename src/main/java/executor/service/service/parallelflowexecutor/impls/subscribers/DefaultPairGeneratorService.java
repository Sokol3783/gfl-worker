package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;

import java.util.ArrayList;
import java.util.List;

public class DefaultPairGeneratorService implements PairGeneratorService {
    private final ProxyQueue proxyQueue;
    private final ScenarioQueue scenarioQueue;

    private final ProxyConfigHolder defaultProxy;

    public DefaultPairGeneratorService(ProxyQueue proxyQueue, ScenarioQueue scenarioQueue, ProxyConfigHolder defaultProxy) {
        this.proxyQueue = proxyQueue;
        this.scenarioQueue = scenarioQueue;
        this.defaultProxy = defaultProxy;
    }


    @Override
    public List<Pair> generatePairs() {
        List<Pair> pairs = new ArrayList<>();
        List<ProxyConfigHolder> proxies;
        List<Scenario> scenarios;
        try {
            proxies = proxyQueue.getAllProxy();
            scenarios = scenarioQueue.getAllScenario();
            if (!scenarios.isEmpty() && !proxies.isEmpty()) {
                if (proxies.size() == scenarios.size()) {
                    pairs = createOneToOnePairs(proxies, scenarios);
                } else {
                    pairs = createMixPairs(proxies, scenarios);
                }
            } else if (!scenarios.isEmpty()) {
                proxies.add(defaultProxy);
                pairs = createMixPairs(proxies, scenarios);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return pairs;
    }

    private List<Pair> createMixPairs(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        int maxSize = Math.max(scenarios.size(), proxies.size());
        List<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < maxSize; i++) {
            Pair pair = new Pair(
                    proxies.get(i % proxies.size()),
                    scenarios.get(i % scenarios.size())
            );
            pairs.add(pair);
        }

        return pairs;
    }

    private List<Pair> createOneToOnePairs(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        List<Pair> pairs = new ArrayList<>(proxies.size());

        for (int i = 0; i < proxies.size(); i++) {
            Pair pair = new Pair(
                    proxies.get(i),
                    scenarios.get(i)
            );
            pairs.add(pair);
        }

        return pairs;
    }
}

