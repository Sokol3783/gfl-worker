package executor.service.service.parallel;

import executor.service.handler.ProxyHandler;
import executor.service.handler.ScenarioHandler;
import executor.service.service.ProxySourcesClient;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

/**
 * Worker task.
 *
 * @author Oleksandr Tuleninov, Yurii Kotsiuba.
 * @version 01
 * */
public class TaskWorker<T> implements Callable<T> {

    private final T listener;

    public TaskWorker(T listener) {
        this.listener = listener;
    }

    @Override
    public T call() {
        BlockingQueue<T> queue = new LinkedBlockingDeque<>();
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
        return (T) queue;
    }

    private ScenarioHandler createScenarioHandler(Consumer<T> consumer) {
        return item -> consumer.accept((T) item);
    }

    private ProxyHandler createProxyHandler(Consumer<T> consumer) {
        return item -> consumer.accept((T) item);
    }
}
