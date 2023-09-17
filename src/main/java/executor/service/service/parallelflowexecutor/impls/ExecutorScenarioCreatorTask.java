package executor.service.service.parallelflowexecutor.impls;

import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Task;
import executor.service.service.scenarios.ScenarioExecutor;

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
