package executor.service.service.parallel;

import executor.service.model.ProxyConfigHolder;
import executor.service.service.ProxySourcesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Proxy task.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ProxyTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ProxyTask.class);
    private static final int DELAY = 3;

    private final ProxySourcesClient client;
    private final Queue<ProxyConfigHolder> PROXY_QUEUE;
    private final CountDownLatch CDL;

    public ProxyTask(ProxySourcesClient client,
                     Queue<ProxyConfigHolder> proxyQueue,
                     CountDownLatch cdl) {
        this.client = client;
        this.PROXY_QUEUE = proxyQueue;
        this.CDL = cdl;
    }

    @Override
    public void run() {
        Flux<ProxyConfigHolder> proxiesFlux = client.getProxies();
        proxiesFlux.subscribe(PROXY_QUEUE::add);
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
