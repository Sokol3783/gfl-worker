package executor.service.model.service;

import executor.service.service.parallelflowexecutor.Operatable;

public class ContinuousOperationNode {
    private final Operatable operatable;
    private Thread thread;

    public ContinuousOperationNode(Operatable operatable) {
        this.operatable = operatable;
    }

    public Operatable getTask() {
        return operatable;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
