package executor.service.service.parallelflowexecutor;

import executor.service.model.service.ContinuousJobNode;

import java.util.List;

public interface ContinuousJobOperator {

    List<ContinuousJobNode> nodes();

    void keepTaskAlive();

}
