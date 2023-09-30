package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Jobable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutionSubscriber implements Jobable {
    //TODO предложения за статик лонг DELAY!
    private static long DELAY = 2000L;
    private final ProxyQueue proxyQueue;
    private final ScenarioQueue scenarioQueue;
    private final ExecutionService executionService;
    private final ParallelFlowExecutorService parallelFlow;

    public ExecutionSubscriber(ProxyQueue proxyQueue, ScenarioQueue scenarioQueue,
                               ExecutionService executionService, ParallelFlowExecutorService parallelFlow) {
        this.proxyQueue = proxyQueue;
        this.scenarioQueue = scenarioQueue;
        this.executionService = executionService;
        //TODO победить плохую двухстороннюю зависимость заодно решить проблему с костылем ObjectFactory
        this.parallelFlow = parallelFlow;
    }

    @Override
    public void run() {

        try {
            while (true) {
                List<ProxyConfigHolder> proxies = proxyQueue.getAllProxy();
                List<Scenario> scenarios = scenarioQueue.getAllScenario();
                /*TODO создать новый сервис у которого будет функционал генерировать из принимаемых
                       листов проксей и сценариев список пар для запуска executionService.execute
                       проблема будет в перенасыщенности класса зависимостями
                       либо выделить сервис в который заинжектены кьюхи и отдает список пар
                       пары вынести в модели и дать нормальное название
                 */
                List<Pair> pair = makePairs(proxies, scenarios);
                pair.forEach(s ->
                                parallelFlow.execute(() -> executionService.execute(s.getScenario(), s.getProxy()))
                        );
                sleep();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private List<Pair> makePairs(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        if (!proxies.isEmpty() && !scenarios.isEmpty()) {
            if (proxies.size() == scenarios.size()){
                return createOneToOnePairs(proxies, scenarios);
            }
            return createMixPairs(proxies, scenarios);
        } else if (proxies.isEmpty() && !scenarios.isEmpty()) {
            //TODO
        }
        return new ArrayList<>();
    }

    private List<Pair> createMixPairs(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {

        int maxSize = Math.max(scenarios.size(), proxies.size());

        return IntStream.range(0, maxSize)
                .mapToObj(i -> new Pair(proxies.get(i % proxies.size()), scenarios.get(i % scenarios.size())))
                .collect(Collectors.toList());
    }

    private List<Pair> createOneToOnePairs(List<ProxyConfigHolder> proxies, List<Scenario> scenarios) {
        List<Pair> pairs = new ArrayList<>(proxies.size());
        for (int i = 0; i < proxies.size(); i++) {
            pairs.add(new Pair(proxies.get(i), scenarios.get(i)));
        }
        return pairs;
    }


    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static class Pair {
        private ProxyConfigHolder proxy;

        public ProxyConfigHolder getProxy() {
            return proxy;
        }

        public Scenario getScenario() {
            return scenario;
        }

        private Scenario scenario;


        public Pair(ProxyConfigHolder proxy, Scenario scenario) {
            this.proxy = proxy;
            this.scenario = scenario;
        }

        public Pair(Scenario scenario) {
            this.scenario = scenario;
        }
    }
}
