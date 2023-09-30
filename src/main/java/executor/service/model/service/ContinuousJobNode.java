package executor.service.model.service;

import executor.service.service.parallelflowexecutor.Jobable;

public class ContinuousJobNode {
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
