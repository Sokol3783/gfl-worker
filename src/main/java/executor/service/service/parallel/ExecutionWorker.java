package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.ExecutionService;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

/**
 * Execution task.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ExecutionWorker implements Runnable {

    private final ExecutionService service;
    private final Queue<Scenario> scenarioQueue;
    private final Queue<ProxyConfigHolder> proxyQueue;
    private final CountDownLatch cdlParallelFlow;

    public ExecutionWorker(ExecutionService service,
                           Queue<Scenario> scenarioQueue,
                           Queue<ProxyConfigHolder> proxyQueue,
                           CountDownLatch cdlParallelFlow) {
        this.service = service;
        this.scenarioQueue = scenarioQueue;
        this.proxyQueue = proxyQueue;
        this.cdlParallelFlow = cdlParallelFlow;
    }

    @Override
    public void run() {
        service.execute(scenarioQueue, proxyQueue);
        cdlParallelFlow.countDown();
    }
}
