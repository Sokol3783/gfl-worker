package executor.service.service.stepexecution;

import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.impl.StepExecutionFabricimpl;

public interface StepExecutionFabric {

   StepExecution getStepExecutor(String stepAction);

   StepExecution getStepExecutor(StepTypes stepAction);

}
