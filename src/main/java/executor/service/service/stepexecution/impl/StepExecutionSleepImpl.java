package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

import static java.lang.Integer.parseInt;

public class StepExecutionSleepImpl implements StepExecutionSleep {

    @Override
    public String getStepAction() {
        return StepTypes.SLEEP.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        System.out.println(getClass().getSimpleName() + "-> we are execute");
        try {
            Thread.sleep(parseInt(step.getValue()) * 1000L);
        } catch (InterruptedException e) {
            throw new StepExecutionException(e);
        }
    }
}
