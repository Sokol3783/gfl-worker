package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.StepAction;
import executor.service.service.stepexecution.StepExecution;
import executor.service.service.stepexecution.StepExecutionFabric;

import java.util.List;

public class StepExecutionFabricImpl implements StepExecutionFabric {

    private final List<StepExecution> stepExecutions;

    public StepExecutionFabricImpl(List<StepExecution> stepExecutions) {
        this.stepExecutions = stepExecutions;
    }

    @Override
    public StepExecution getStepExecutor(String stepAction) {
        return stepExecutions.stream().filter(s -> s.getNameStepAction().compareTo(StepAction.valueOf(stepAction).getName()) == 0)
                .findFirst().orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
    }

    public StepExecution getStepExecutor(StepAction stepType) {
        return stepExecutions.stream()
                .filter(s -> s.getStepAction() == stepType)
                .findFirst()
                .orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
    }


}
