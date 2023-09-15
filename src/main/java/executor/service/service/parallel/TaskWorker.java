package executor.service.service.parallel;

import executor.service.handler.ProxyHandler;
import executor.service.handler.ScenarioHandler;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 * Worker task.
 *
 * @author Oleksandr Tuleninov, Yurii Kotsiuba.
 * @version 01
 * */
public class TaskWorker<T> implements Runnable {

    private final T listener;
    private final BlockingQueue<T> queue;

    public TaskWorker(T listener, BlockingQueue<T> queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public void run() {
        Consumer<T> itemHandlerConsumer = items -> {
            try {
                queue.put(items);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        if (listener instanceof ScenarioSourceListener) {
            ((ScenarioSourceListener) listener).execute(createScenarioHandler(itemHandlerConsumer));
        } else if (listener instanceof ProxySourcesClient) {
            ((ProxySourcesClient) listener).execute(createProxyHandler(itemHandlerConsumer));
        }
    }

    private ScenarioHandler createScenarioHandler(Consumer<T> consumer) {
        return item -> consumer.accept((T) item);
    }

    private ProxyHandler createProxyHandler(Consumer<T> consumer) {
        return item -> consumer.accept((T) item);
    }
}
