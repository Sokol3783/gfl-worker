package executor.service.handler;

/**
 * Item Retrieval Function.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ItemHandler<T> {

    void onScenarioReceived(T item);

}
