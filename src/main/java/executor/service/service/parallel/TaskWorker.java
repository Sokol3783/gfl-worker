package executor.service.service.parallel;

import executor.service.service.ItemHandler;
import executor.service.service.ScenarioSourceListener;

import java.util.concurrent.BlockingQueue;

public class TaskWorker<T> implements Runnable {

    private final T listener;
    private final BlockingQueue<T> queue;

    public TaskWorker(T listener, BlockingQueue<T> queue) {
        this.listener = listener;
        this.queue = queue;
    }

    @Override
    public void run() {
        if(listener instanceof ScenarioSourceListener) {
            ((ScenarioSourceListener)listener).execute(getItemHandler());
        } else {
            listener.execute(getItemHandler());
        }
    }

    private ItemHandler getItemHandler() {
        return items -> {
            try {
                queue.put((T) items);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }
}
