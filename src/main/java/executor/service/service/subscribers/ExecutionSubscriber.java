package executor.service.service.subscribers;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.ExecutionService;

import java.util.concurrent.ExecutorService;

public class ExecutionSubscriber implements Runnable {
    private final ProxyQueue proxyQueue;
    private final ScenarioQueue scenarioQueue;
    private final ExecutionService executionService;
    private final ExecutorService threadPool;

    public ExecutionSubscriber(ProxyQueue proxyQueue, ScenarioQueue scenarioQueue, ExecutionService executionService, ExecutorService threadPool) {
        this.proxyQueue = proxyQueue;
        this.scenarioQueue = scenarioQueue;
        this.executionService = executionService;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ProxyConfigHolder proxy = proxyQueue.getProxy();
                Scenario scenario = scenarioQueue.getScenario();
                threadPool.execute(() -> executionService.execute(scenario, proxy));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
