package executor.service.service.stepexecution;

import executor.service.model.Step;
import org.openqa.selenium.WebDriver;

public interface StepExecutionSleep extends StepExecution{

    String getStepAction();

    void step(WebDriver webDriver, Step step);

}
