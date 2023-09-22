package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.service.stepexecution.StepExecution;

import java.util.List;

public class StepExecutionFabric implements executor.service.service.stepexecution.StepExecutionFabric {

    private final List<StepExecution> stepExecutions;

    public StepExecutionFabric(List<StepExecution> stepExecutions) {
        this.stepExecutions = stepExecutions;
    }

    @Override
    public StepExecution getStepExecutor(String stepAction) {
        return stepExecutions.stream().filter(s -> s.getStepAction().compareTo(stepAction) == 0)
                .findFirst().orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
    }


}
