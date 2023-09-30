package executor.service.service.stepexecution;

import executor.service.model.scenario.StepAction;

public interface StepExecutionFabric {

   StepExecution getStepExecutor(String stepAction);

   StepExecution getStepExecutor(StepAction stepAction);

}
