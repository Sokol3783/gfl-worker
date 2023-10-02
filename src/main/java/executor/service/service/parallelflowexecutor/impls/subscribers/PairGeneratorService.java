package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

import java.util.List;

public interface PairGeneratorService {
    List<Pair> generatePairs();
}
