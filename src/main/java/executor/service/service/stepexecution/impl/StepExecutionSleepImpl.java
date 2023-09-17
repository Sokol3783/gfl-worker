package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

public class StepExecutionSleepImpl implements StepExecutionSleep {

  @Override
  public String getStepAction() {
    return StepTypes.SLEEP.getName();
  }

  @Override
  public void step(WebDriver webDriver, Step step) {
    try {
      Thread.sleep(Integer.valueOf(step.getValue()) * 1000);
    } catch (InterruptedException e) {
      throw new StepExecutionException(e);
    }
  }
}
