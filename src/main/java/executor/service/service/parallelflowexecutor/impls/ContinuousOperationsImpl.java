package executor.service.service.parallelflowexecutor.impls;

import executor.service.model.service.ContinuousOperationNode;
import executor.service.service.parallelflowexecutor.ContinuousOperations;

import java.util.List;
import java.util.concurrent.ThreadFactory;

public record ContinuousOperationsImpl(List<ContinuousOperationNode> nodes, ThreadFactory factory) implements ContinuousOperations {

    @Override
    public List<ContinuousOperationNode> getContinuousOperations() {
        return List.copyOf(nodes);
    }

    @Override
    public void startInterruptedJob() {
        getContinuousOperations().stream().filter(s -> s.getThread() == null ||
                        !s.getThread().isAlive()).
                forEach(s -> {
                            Thread thread = factory.newThread(s.getTask());
                            thread.start();
                            s.setThread(thread);
                        }
                );
    }
}
