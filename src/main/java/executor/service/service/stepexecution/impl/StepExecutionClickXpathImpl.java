package executor.service.service.stepexecution.impl;

import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.StepExecutionClickXpath;
import org.openqa.selenium.WebDriver;

public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

    @Override
    public String getStepAction() {
        return StepTypes.CLICK_XPATH.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        System.out.println(getClass().getSimpleName() + "-> we are execute");
    }
}
