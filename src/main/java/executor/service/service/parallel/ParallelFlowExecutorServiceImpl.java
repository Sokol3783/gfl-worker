package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private static final Logger log = LoggerFactory.getLogger(ParallelFlowExecutorServiceImpl.class);
    private static boolean FLAG = true;

    private final ExecutorService threadPoolExecutor;
    private final ExecutionService service;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;
    private final TasksFactory tasksFactory;
    private ProxyConfigHolder defaultProxy;
    private final ProxyValidator proxyValidator;

    public ParallelFlowExecutorServiceImpl(ExecutorService threadPoolExecutor,
                                           ExecutionService service,
                                           ScenarioSourceListener scenarioSourceListener,
                                           ProxySourcesClient proxySourcesClient,
                                           TasksFactory tasksFactory,
                                           ProxyConfigHolder defaultProxy, ProxyValidator proxyValidator) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.service = service;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.tasksFactory = tasksFactory;
        this.defaultProxy = defaultProxy;
        this.proxyValidator = proxyValidator;
    }

    /**
     * Start ScenarioSourceListener, ProxySourcesClient, ExecutionService
     * in parallel multi-threaded mode.
     */
    @Override
    public void execute() {
        Future<BlockingQueue<Scenario>> futureScenarios
                = threadPoolExecutor.submit(tasksFactory.createTaskWorker(scenarioSourceListener));

        Future<BlockingQueue<ProxyConfigHolder>> futureProxies
                = threadPoolExecutor.submit(tasksFactory.createTaskWorker(proxySourcesClient));

        executeScenarioAndProxy(futureScenarios, futureProxies);
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

    private void executeScenarioAndProxy(Future<BlockingQueue<Scenario>> futureScenarios,
                                         Future<BlockingQueue<ProxyConfigHolder>> futureProxies) {
        try {
            executeParallel(futureScenarios, futureProxies);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread was interrupted in ParallelFlowExecutorServiceImpl.class", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Execute the scenario and proxy in parallel mode.
     *
     * @param futureScenarios queue with scenarios
     * @param futureProxies queue with proxies
     */
    private void executeParallel(Future<BlockingQueue<Scenario>> futureScenarios,
                                 Future<BlockingQueue<ProxyConfigHolder>> futureProxies)
            throws InterruptedException, ExecutionException {
        BlockingQueue<Scenario> scenarios = futureScenarios.get();
        BlockingQueue<ProxyConfigHolder> proxies = futureProxies.get();
        Scenario scenario;
        ProxyConfigHolder proxy;
        while (FLAG) {
            scenario = scenarios.take();
            proxy = proxies.poll();
            if (proxy != null && proxyValidator.isValid(proxy)) defaultProxy = proxy;
            threadPoolExecutor.execute(tasksFactory.createExecutionWorker(service, scenario, defaultProxy));
        }
    }
}
