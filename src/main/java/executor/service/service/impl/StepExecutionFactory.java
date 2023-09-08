package executor.service.service.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.service.StepExecution;
import executor.service.service.StepExecutionFabric;
import java.util.List;

public class StepExecutionFactory implements StepExecutionFabric {

  private final List<StepExecution> stepExecutions;

  public StepExecutionFactory(List<StepExecution> stepExecutions) {
    this.stepExecutions = stepExecutions;
  }

  @Override
  public StepExecution getStepExecutor(String stepAction) {
    return stepExecutions.stream().filter(s -> s.getStepAction().compareTo(stepAction) == 0)
        .findFirst().orElseThrow(() -> new StepExecutionException("Unsupported type execution!"));
  }


}
