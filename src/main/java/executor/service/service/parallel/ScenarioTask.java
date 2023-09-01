package executor.service.service.parallel;

import executor.service.model.Scenario;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Scenario task.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ScenarioTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ScenarioTask.class);
    private static final int DELAY = 3;

    private final ScenarioSourceListener listener;
    private final Queue<Scenario> SCENARIO_QUEUE;
    private final CountDownLatch CDL;

    public ScenarioTask(ScenarioSourceListener listener,
                        Queue<Scenario> scenarioQueue,
                        CountDownLatch cdl) {
        this.listener = listener;
        this.SCENARIO_QUEUE = scenarioQueue;
        this.CDL = cdl;
    }

    @Override
    public void run() {
        Flux<Scenario> scenariosFlux = listener.getScenarios();
        scenariosFlux.subscribe(SCENARIO_QUEUE::add);
        getDelay();
        CDL.countDown();
    }

    private void getDelay() {
        try {
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (InterruptedException e) {
            log.info("Thread was interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
