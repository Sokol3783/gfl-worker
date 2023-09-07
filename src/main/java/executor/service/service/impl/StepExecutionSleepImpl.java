package executor.service.service.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.Step;
import executor.service.model.StepTypes;
import executor.service.service.StepExecutionSleep;
import java.util.Random;
import org.openqa.selenium.WebDriver;

public class StepExecutionSleepImpl implements StepExecutionSleep {

  @Override
  public String getStepAction() {
    return StepTypes.SLEEP.getName();
  }

  @Override
  public void step(WebDriver webDriver, Step step) {
    try {
      Thread.sleep(getRandom(step.getValue()));
    } catch (InterruptedException e) {
      throw new StepExecutionException(e);
    }

  }

  private int getRandom(String value) {

    String values[] = value.split(":");
    Random random = new Random();
    int min = Integer.parseInt(values[0]);
    int max = Integer.parseInt(values[values.length - 1]);

    return random.ints(min, max + 1)
        .findFirst().orElse(1);

  }


}
