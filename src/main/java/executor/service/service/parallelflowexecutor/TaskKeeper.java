package executor.service.service.parallelflowexecutor;

import java.util.List;

public interface TaskKeeper {
    boolean taskNotAlive();

    List<TaskNode> nodes();

    class TaskNode {

        final Task task;
        Thread thread;

        public TaskNode(Task task) {
            this.task = task;
        }

        public Task getTask() {
            return task;
        }

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }
    }
}
