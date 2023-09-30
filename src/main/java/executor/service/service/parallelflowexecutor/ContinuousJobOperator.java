package executor.service.service.parallelflowexecutor;

import java.util.List;

public interface ContinuousJobOperator {

    List<ContinuousJobNode> nodes();

    void keepTaskAlive();

    //TODO вынести в model и создании через Builder
    class ContinuousJobNode {

        private final Jobable jobable;
        private Thread thread;

        public ContinuousJobNode(Jobable jobable) {
            this.jobable = jobable;
        }

        public Jobable getTask() {
            return jobable;
        }

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }
    }
}
