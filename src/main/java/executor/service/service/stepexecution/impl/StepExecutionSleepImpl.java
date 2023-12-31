package executor.service.service.stepexecution.impl;

import executor.service.exceptions.StepExecutionException;
import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepAction;
import executor.service.service.stepexecution.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

import static java.lang.Integer.parseInt;

//TODO дописать логирование
public class StepExecutionSleepImpl implements StepExecutionSleep {


    @Override
    public String getNameStepAction() {
        return StepAction.SLEEP.getName();
    }

    @Override
    public StepAction getStepAction() {
        return StepAction.SLEEP;
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
