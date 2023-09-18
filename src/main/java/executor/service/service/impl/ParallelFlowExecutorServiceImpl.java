package executor.service.service.impl;

import executor.service.model.ProxyQueue;
import executor.service.model.ScenarioQueue;
import executor.service.service.ExecutionService;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.ExecutorService;

public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {
    private final ProxyQueue proxyQueue;
    private final ScenarioQueue scenarioQueue;
    private final ExecutorService executor;
    private final ExecutionService executionService;
    private final ScenarioSourceListener scenarioListener;
    private final ProxySourcesClient proxyClient;

    public ParallelFlowExecutorServiceImpl(ProxyQueue proxyQueue,
                                           ScenarioQueue scenarioQueue,
                                           ExecutorService executor,
                                           ExecutionService executionService,
                                           ScenarioSourceListener scenarioListener,
                                           ProxySourcesClient proxyClient) {
        this.proxyQueue = proxyQueue;
        this.scenarioQueue = scenarioQueue;
        this.executor = executor;
        this.executionService = executionService;
        this.scenarioListener = scenarioListener;
        this.proxyClient = proxyClient;
    }

    @Override
    public void execute() {
        ProxyPublisher proxyPublisher = new ProxyPublisher(proxyQueue, proxyClient);
        ScenarioPublisher scenarioPublisher = new ScenarioPublisher(scenarioQueue, scenarioListener);

        ExecutionSubscriber subscriber = new ExecutionSubscriber(proxyQueue, scenarioQueue, executionService, executor);

        executor.execute(proxyPublisher);
        executor.execute(scenarioPublisher);
        executor.execute(subscriber);
    }
}
