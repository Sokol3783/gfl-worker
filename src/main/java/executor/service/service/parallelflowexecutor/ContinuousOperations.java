package executor.service.service.parallelflowexecutor;

import executor.service.model.service.ContinuousOperationNode;

import java.util.List;
import java.util.Queue;

public interface ContinuousOperations {

    List<ContinuousOperationNode> getContinuousOperations();

    void startInterruptedOperation(ParallelFlowExecutorService service, Queue<Runnable> queue);

}
