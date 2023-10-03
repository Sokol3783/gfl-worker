package executor.service.service.parallelflowexecutor;

import executor.service.model.service.ContinuousOperationNode;

import java.util.List;

public interface ContinuousOperations {

    List<ContinuousOperationNode> getContinuousJobs();

    void startInterruptedJob();

}
