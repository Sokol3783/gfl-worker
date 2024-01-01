package executor.service.queue;

import java.util.Collection;

public interface DataPusher<T>{

    boolean addValue(T value);

    boolean addValue(Collection<T> value);

    int fillPercent();

    int isFull();

}
