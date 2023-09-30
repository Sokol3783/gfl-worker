package executor.service.service.stepexecution;

import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import org.openqa.selenium.WebDriver;

public interface StepExecutionSleep extends StepExecution{

    String getNameStepAction();

    StepTypes getStepAction();

    void step(WebDriver webDriver, Step step);

}
