package executor.service.service.stepexecution;

import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import org.openqa.selenium.WebDriver;

public interface StepExecutionClickXpath extends StepExecution{

    String getNameStepAction();

    StepTypes stepAction();

    void step(WebDriver webDriver, Step step);

}
