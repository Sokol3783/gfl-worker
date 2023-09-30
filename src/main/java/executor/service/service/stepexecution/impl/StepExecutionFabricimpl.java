package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.StepExecution;
import executor.service.service.stepexecution.StepExecutionFabric;

import java.util.List;

//TODO расширить добавив метод на прием ENUM StepType
public class StepExecutionFabricimpl implements StepExecutionFabric {

    private final List<StepExecution> stepExecutions;

    public StepExecutionFabricimpl(List<StepExecution> stepExecutions) {
        this.stepExecutions = stepExecutions;
    }

    @Override
    public StepExecution getStepExecutor(String stepAction) {
        return stepExecutions.stream().filter(s -> s.getStepAction().compareTo(StepTypes.valueOf(stepAction).getName()) == 0)
                .findFirst().orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
    }


}