package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;

public class DefaultPairGeneratorService implements PairGeneratorService {
    private final List<ProxyConfigHolder> proxies;
    private final List<Scenario> scenarios;

    public DefaultPairGeneratorService(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        this.proxies = proxies;
        this.scenarios = scenarios;
    }

    @Override
    public List<Pair> generatePairs() {
        List<Pair> pairs = new ArrayList<>();

        if (!proxies.isEmpty() && !scenarios.isEmpty()) {
            if (proxies.size() == scenarios.size()) {
                pairs = createOneToOnePairs(proxies, scenarios);
            } else {
                pairs = createMixPairs(proxies, scenarios);
            }
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

