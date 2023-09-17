package executor.service.service.parallelflowexecutor.tasksimpl;

import executor.service.service.parallelflowexecutor.TaskKeeper;

import java.util.List;

public record TaskKeeperImpl(List<TaskNode> nodes) implements TaskKeeper {


    @Override
    public boolean taskNotAlive() {
        if (!nodes.isEmpty()) {
            return nodes.stream().anyMatch(s -> s.getThread() == null
                    || s.getThread().isAlive());
        }
        return false;
    }

    @Override
    public List<TaskNode> nodes() {
        return List.copyOf(nodes);
    }
}
