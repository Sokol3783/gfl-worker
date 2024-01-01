package executor.service.queue;

import executor.service.exceptions.DataFetchException;

import java.util.Collection;

public interface DataFetcher<T> {

    T fetchValue();

    Collection<T> fetchValues(int count) throws DataFetchException;

    Collection<T> fetchAllValue();

}
