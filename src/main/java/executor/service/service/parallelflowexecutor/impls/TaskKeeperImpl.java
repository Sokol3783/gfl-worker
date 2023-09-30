package executor.service.service.parallelflowexecutor.impls;

import executor.service.service.parallelflowexecutor.TaskKeeper;

import java.util.List;
import java.util.concurrent.ThreadFactory;

public record TaskKeeperImpl(List<TaskNode> nodes, ThreadFactory factory) implements TaskKeeper {

    @Override
    public List<TaskNode> nodes() {
        return List.copyOf(nodes);
    }

    @Override
    public void keepTaskAlive() {
        nodes().stream().filter(s -> s.getThread() == null ||
                        !s.getThread().isAlive()).
                forEach(s -> {
                            Thread thread = factory.newThread(s.getTask());
                            thread.start();
                            s.setThread(thread);
                        }
                );
    }
}
