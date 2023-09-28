package executor.service.service.stepexecution;


import executor.service.model.scenario.Step;
import org.openqa.selenium.WebDriver;

//TODO расширить научив отдавать StepType
public interface StepExecution {

  String getStepAction();

  void step(WebDriver webDriver, Step step);

}
