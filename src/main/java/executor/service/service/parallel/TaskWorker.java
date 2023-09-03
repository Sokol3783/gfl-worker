package executor.service.service.parallel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class TaskWorker<T> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(TaskWorker.class);
    private final CountDownLatch cdlFlux = new CountDownLatch(1);

    private final Flux<T> flux;
    private final Queue<T> queue;
    private final CountDownLatch cdlParallelFlow;

    public TaskWorker(Flux<T> flux, Queue<T> queue,
                      CountDownLatch cdlParallelFlow) {
        this.flux = flux;
        this.queue = queue;
        this.cdlParallelFlow = cdlParallelFlow;
    }

    @Override
    public void run() {
        flux.subscribe(
                queue::add,
                error -> {
                    log.info("Flux thread was interrupted");
                    Thread.currentThread().interrupt();
                },
                cdlFlux::countDown
        );
        awaitFlux();
        cdlParallelFlow.countDown();
    }

    /**
     * Wait for an asynchronous Flux thread to finish.
     */
    private void awaitFlux() {
        try {
            cdlFlux.await();
        } catch (InterruptedException e) {
            log.info("ScenarioTask thread was interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
