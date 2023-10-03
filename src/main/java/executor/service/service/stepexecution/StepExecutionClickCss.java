package executor.service.service.stepexecution;

import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepAction;
import org.openqa.selenium.WebDriver;

public interface StepExecutionClickCss extends StepExecution {

    String getNameStepAction();

    StepAction getStepAction();

    void step(WebDriver webDriver, Step step);

}
