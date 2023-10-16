package executor.service.model.service;

import executor.service.service.parallelflowexecutor.Operatable;

public class ContinuousOperationNode {
    private final Operatable operatable;

    public ContinuousOperationNode(Operatable operatable) {
        this.operatable = operatable;
    }

    public Operatable getTask() {
        return operatable;
    }

}
