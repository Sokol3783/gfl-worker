package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ParalleFlowExecutorService {

    private static final Logger log = LoggerFactory.getLogger(ParalleFlowExecutorService.class);

    private static final Queue<Scenario> SCENARIO_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<ProxyConfigHolder> PROXY_QUEUE = new ConcurrentLinkedQueue<>();
    private static final int NUMBER_TIMES = 3;
    private static final CountDownLatch cdlParallelFlow = new CountDownLatch(NUMBER_TIMES);

    private final ExecutorService threadPoolExecutor;
    private final ExecutionService service;
    private final ScenarioSourceListener scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;

    public ParalleFlowExecutorService(ExecutorService threadPoolExecutor,
                                      ExecutionService service,
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
    public void execute() {
        threadPoolExecutor.execute(new TaskWorker<>(scenarioSourceListener.getScenarios(), SCENARIO_QUEUE, cdlParallelFlow));

        threadPoolExecutor.execute(new TaskWorker<>(proxySourcesClient.getProxies(), PROXY_QUEUE, cdlParallelFlow));

        threadPoolExecutor.execute(new ExecutionWorker(service, SCENARIO_QUEUE, PROXY_QUEUE, cdlParallelFlow));

        await();
        threadPoolExecutor.shutdown();
    }

    /**
     * Wait for the Workers threads to complete.
     */
    private void await() {
        try {
            cdlParallelFlow.await();
        } catch (InterruptedException e) {
            log.info("Thread was interrupted by await");
            Thread.currentThread().interrupt();
        }
    }
}
