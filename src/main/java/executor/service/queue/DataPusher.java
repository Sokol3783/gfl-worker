package executor.service.queue;

import executor.service.exceptions.DataPushException;

import java.util.Collection;

public interface DataPusher<T>{

    boolean addValue(T value) throws DataPushException;

    boolean addAllValue(Collection<T> value) throws DataPushException;

    int fillPercent();

    int isFull();

}
