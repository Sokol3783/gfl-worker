package executor.service.service.parallelflowexecutor.impls;

import executor.service.model.service.ContinuousOperationNode;
import executor.service.service.parallelflowexecutor.ContinuousOperations;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;

public class ContinuousOperationsImpl implements ContinuousOperations {
    private final  List<ContinuousOperationNode> nodes;

    private boolean isActive;

    public ContinuousOperationsImpl(List<ContinuousOperationNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public List<ContinuousOperationNode> getContinuousOperations() {
        return List.copyOf(nodes);
    }

    @Override
    public void startInterruptedOperation(ParallelFlowExecutorService service, Queue<Runnable> queue) {

        if (!isActive) {
            isActive = true;
            nodes.stream().filter( s -> queue.stream().noneMatch(q -> q.equals(s))).forEach(
                    s -> service.execute(s.getTask()));
            isActive = false;
        }



    }
}
