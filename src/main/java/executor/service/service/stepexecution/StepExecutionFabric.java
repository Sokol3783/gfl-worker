package executor.service.service.stepexecution;

import executor.service.model.scenario.StepTypes;

public interface StepExecutionFabric {

   StepExecution getStepExecutor(String stepAction);

   StepExecution getStepExecutor(StepTypes stepAction);

}
