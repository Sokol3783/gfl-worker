package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyQueue;
import executor.service.model.Scenario;
import executor.service.model.ScenarioQueue;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.Task;

public class ExecutorScenarioCreatorTask implements Task {
    private final ScenarioQueue scenarioQueue;
    private final ProxyQueue proxyQueue;
    private final ParallelFlowExecutorService executorService;
    private final ScenarioExecutor executor;

    public ExecutorScenarioCreatorTask(ScenarioQueue scenarioQueue,
                                       ProxyQueue proxyQueue,
                                       ParallelFlowExecutorService executorService,
                                       ScenarioExecutor executor) {
        this.scenarioQueue = scenarioQueue;
        this.proxyQueue = proxyQueue;
        this.executorService = executorService;
        this.executor = executor;
    }

    @Override
    public void run() {

    }
}
