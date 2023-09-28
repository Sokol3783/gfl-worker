package executor.service.service.parallelflowexecutor;

import java.util.List;

//TODO дать более полно описывающие имя
public interface TaskKeeper {
    boolean taskNotAlive();

    List<TaskNode> nodes();

    //TODO вынести в model и создании через Builder
    class TaskNode {

        private final Task task;
        private Thread thread;

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
