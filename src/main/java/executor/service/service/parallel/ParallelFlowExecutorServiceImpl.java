package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.impl.ExecutionServiceImpl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private static final BlockingQueue<Scenario> SCENARIO_QUEUE = new LinkedBlockingQueue<>();
    private static final BlockingQueue<ProxyConfigHolder> PROXY_QUEUE = new LinkedBlockingQueue<>();

    private final ExecutorService threadPoolExecutor;
    private final ExecutionServiceImpl service;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;

    public ParallelFlowExecutorServiceImpl(ExecutorService threadPoolExecutor,
                                           ExecutionServiceImpl service,
                                           ScenarioSourceListener scenarioSourceListener,
                                           ProxySourcesClient proxySourcesClient) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.service = service;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
    }

    /**
     * Start ScenarioSourceListener, ProxySourcesClient, ExecutionService
     * in parallel multi-threaded mode.
     */
    @Override
    public void execute() {
        threadPoolExecutor.execute(new TaskWorker<>(scenarioSourceListener.getScenarios(), SCENARIO_QUEUE));

        threadPoolExecutor.execute(new TaskWorker<>(proxySourcesClient.getProxies(), PROXY_QUEUE));

        threadPoolExecutor.execute(new ExecutionWorker(service, SCENARIO_QUEUE, PROXY_QUEUE));
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed,
     * but no new tasks will be accepted.
     * */
    @Override
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }
}
