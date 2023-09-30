package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.StepExecution;
import executor.service.service.stepexecution.StepExecutionFabric;

import java.util.List;

public class StepExecutionFabricimpl implements StepExecutionFabric {

    private final List<StepExecution> stepExecutions;

    public StepExecutionFabricimpl(List<StepExecution> stepExecutions) {
        this.stepExecutions = stepExecutions;
    }

    @Override
    public StepExecution getStepExecutor(String stepAction) {
        return stepExecutions.stream().filter(s -> s.getNameStepAction().compareTo(StepTypes.valueOf(stepAction).getName()) == 0)
                .findFirst().orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
    }

    public StepExecution getStepExecutor(StepTypes stepType) {
        return stepExecutions.stream()
                .filter(s -> s.getStepAction() == stepType)
                .findFirst()
                .orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
    }


}
