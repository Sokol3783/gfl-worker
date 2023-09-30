package executor.service.service.stepexecution;


import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import org.openqa.selenium.WebDriver;


public interface StepExecution {

  String getNameStepAction();

  void step(WebDriver webDriver, Step step);

  StepTypes getStepAction();
}
