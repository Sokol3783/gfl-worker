package executor.service.service;

public interface ItemHandler<T> {

    void onScenarioReceived(T item);

}
