package executor.service.service.stepexecution;

public interface StepExecutionFabric {

   StepExecution getStepExecutor(String stepAction);

}
