package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Task;

public class ExecutionSubscriber implements Task {
    private final ProxyQueue proxyQueue;
    private final ScenarioQueue scenarioQueue;
    private final ExecutionService executionService;
    private final ParallelFlowExecutorService parallelFlow;

    public ExecutionSubscriber(ProxyQueue proxyQueue, ScenarioQueue scenarioQueue,
                               ExecutionService executionService, ParallelFlowExecutorService parallelFlow) {
        this.proxyQueue = proxyQueue;
        this.scenarioQueue = scenarioQueue;
        this.executionService = executionService;
        this.parallelFlow = parallelFlow;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ProxyConfigHolder proxy = proxyQueue.getProxy();
                Scenario scenario = scenarioQueue.getScenario();
                parallelFlow.execute(() -> executionService.execute(scenario, proxy));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
