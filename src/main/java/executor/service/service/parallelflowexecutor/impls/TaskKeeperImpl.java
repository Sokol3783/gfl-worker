package executor.service.service.parallelflowexecutor.impls;

import executor.service.model.service.ContinuousJobNode;
import executor.service.service.parallelflowexecutor.ContinuousJobOperator;

import java.util.List;
import java.util.concurrent.ThreadFactory;

public record TaskKeeperImpl(List<ContinuousJobNode> nodes, ThreadFactory factory) implements ContinuousJobOperator {

    @Override
    public List<ContinuousJobNode> getContinuousJobs() {
        return List.copyOf(nodes);
    }

    @Override
    public void startInterruptedJob() {
        getContinuousJobs().stream().filter(s -> s.getThread() == null ||
                        !s.getThread().isAlive()).
                forEach(s -> {
                            Thread thread = factory.newThread(s.getTask());
                            thread.start();
                            s.setThread(thread);
                        }
                );
    }
}
