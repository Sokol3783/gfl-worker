package executor.service.queue;

import executor.service.exceptions.QueueStorageException;

import java.util.List;

public interface DataFetcher<T> {

    T fetchValue();

    List<T> fetchValues(int count) throws QueueStorageException;

    List<T> fetchAllValue();

}
