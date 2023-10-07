package executor.service.service.parallelflowexecutor.impls;

import executor.service.model.service.ContinuousOperationNode;
import executor.service.service.parallelflowexecutor.ContinuousOperations;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;

public record ContinuousOperationsImpl(List<ContinuousOperationNode> nodes, ThreadFactory factory) implements ContinuousOperations {

    @Override
    public List<ContinuousOperationNode> getContinuousOperations() {
        return List.copyOf(nodes);
    }

    @Override
    public void startInterruptedOperation(ParallelFlowExecutorService service, Queue<? extends Runnable> queue) {

        List<ContinuousOperationNode> list = nodes.stream().filter(s -> queue.stream().noneMatch(q -> q.equals(s))).toList();
        System.out.println("Non runned tasks " + list.size());

        nodes.stream().filter( s -> queue.stream().noneMatch(q -> q.equals(s))).forEach(
                s -> service.execute(s.getTask()));


    }
}
