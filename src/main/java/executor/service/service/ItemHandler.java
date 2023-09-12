package executor.service.service;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ItemHandler<T> {

    void onScenarioReceived(T item);

}
