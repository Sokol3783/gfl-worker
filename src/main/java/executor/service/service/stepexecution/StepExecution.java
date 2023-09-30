package executor.service.service.stepexecution;


import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepAction;
import org.openqa.selenium.WebDriver;


public interface StepExecution {

  String getNameStepAction();

  void step(WebDriver webDriver, Step step);

  StepAction getStepAction();
}
