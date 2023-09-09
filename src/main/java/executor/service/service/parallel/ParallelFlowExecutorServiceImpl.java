package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.*;
import executor.service.service.impl.ExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(ParallelFlowExecutorServiceImpl.class);

    private static final BlockingQueue<Scenario> SCENARIO_QUEUE = new LinkedBlockingQueue<>();
    private static final BlockingQueue<ProxyConfigHolder> PROXY_QUEUE = new LinkedBlockingQueue<>();
    private static boolean FLAG = true;

    private final ExecutorService threadPoolExecutor;
    private final ExecutionService service;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;
    private final ThreadFactory threadFactory;
    private ProxyConfigHolder defaultProxy;
    private final ProxyValidator proxyValidator;

    public ParallelFlowExecutorServiceImpl(ExecutorService threadPoolExecutor,
                                           ExecutionService service,
                                           ScenarioSourceListener scenarioSourceListener,
                                           ProxySourcesClient proxySourcesClient,
                                           ThreadFactory threadFactory,
                                           ProxyConfigHolder defaultProxy, ProxyValidator proxyValidator) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.service = service;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.threadFactory = threadFactory;
        this.defaultProxy = defaultProxy;
        this.proxyValidator = proxyValidator;
    }

    /**
     * Start ScenarioSourceListener, ProxySourcesClient, ExecutionService
     * in parallel multi-threaded mode.
     */
    @Override
    public void execute() {
        threadPoolExecutor.execute(threadFactory.createTaskWorker(scenarioSourceListener, SCENARIO_QUEUE));

        threadPoolExecutor.execute(threadFactory.createTaskWorker(proxySourcesClient, PROXY_QUEUE));

        executeScenarioAndProxy();
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed,
     * but no new tasks will be accepted.
     */
    @Override
    public void shutdown() {
        FLAG = false;
        threadPoolExecutor.shutdown();
    }

    private void executeScenarioAndProxy() {
        try {
            executeParallel();
        } catch (InterruptedException e) {
            log.error("Thread was interrupted in ParallelFlowExecutorServiceImpl.class", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Execute the scenario and proxy in parallel mode.
     */
    private void executeParallel() throws InterruptedException {
        Scenario scenario;
        ProxyConfigHolder proxy;
        while (FLAG) {
            scenario = SCENARIO_QUEUE.take();
            proxy = PROXY_QUEUE.poll();
            if (proxy != null && proxyValidator.isValid(proxy)) defaultProxy = proxy;
            threadPoolExecutor.execute(threadFactory.createExecutionWorker(service, scenario, defaultProxy));
        }
    }
}
