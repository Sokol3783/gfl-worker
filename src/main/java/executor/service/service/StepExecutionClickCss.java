package executor.service.service;

import executor.service.model.Step;
import org.openqa.selenium.WebDriver;

public interface StepExecutionClickCss extends StepExecution {

    String getStepAction();

    void step(WebDriver webDriver, Step step);

}
