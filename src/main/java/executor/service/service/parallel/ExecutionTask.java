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
public class ExecutionTask implements Runnable {

    private final ExecutionService service;
    private final Queue<Scenario> SCENARIO_QUEUE;
    private final Queue<ProxyConfigHolder> PROXY_QUEUE;
    private final CountDownLatch CDL;

    public ExecutionTask(ExecutionService service,
                         Queue<Scenario> scenarioQueue,
                         Queue<ProxyConfigHolder> proxyQueue,
                         CountDownLatch cdl) {
        this.service = service;
        SCENARIO_QUEUE = scenarioQueue;
        this.PROXY_QUEUE = proxyQueue;
        this.CDL = cdl;
    }

    @Override
    public void run() {
        service.execute(SCENARIO_QUEUE, PROXY_QUEUE);
        CDL.countDown();
    }
}
