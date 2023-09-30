package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

import static java.lang.Integer.parseInt;

//TODO дописать логирование
public class StepExecutionSleepImpl implements StepExecutionSleep {

    @Override
    public String getNameStepAction() {
        return StepTypes.SLEEP.getName();
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        try {
            Thread.sleep(parseInt(step.getValue()) * 1000L);
        } catch (InterruptedException e) {
            throw new StepExecutionException(e);
        }
    }
}
