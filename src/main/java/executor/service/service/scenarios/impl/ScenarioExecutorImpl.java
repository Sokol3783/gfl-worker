package executor.service.service.scenarios.impl;

import executor.service.model.scenario.Scenario;
import executor.service.model.scenario.Step;
import executor.service.service.scenarios.ScenarioExecutor;
import executor.service.service.stepexecution.StepExecution;
import executor.service.service.stepexecution.StepExecutionFabric;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ScenarioExecutorImpl implements ScenarioExecutor {

    private final StepExecutionFabric fabric;

    public ScenarioExecutorImpl(StepExecutionFabric fabric) {
        this.fabric = fabric;
    }


    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        webDriver.get(scenario.getSite());
        List<Step> steps = scenario.getSteps();
        for (Step step: steps) {
            StepExecution executor = fabric.getStepExecutor(String.valueOf(step.getAction()));
            executor.step(webDriver, step);

        }
        webDriver.close();
    }
}
