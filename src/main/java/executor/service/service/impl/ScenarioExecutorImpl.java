package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.service.ScenarioExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Class for reading properties from properties file.
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class ScenarioExecutorImpl implements ScenarioExecutor {

    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        for (Step step : scenario.getSteps()) {
            String action = step.getAction().getName();
            switch (action) {
                case "clickCss" -> new StepExecutionClickCssImpl().step(webDriver, step);
                case "sleep" -> new StepExecutionClickXpathImpl().step(webDriver, step);
                case "clickXpath" -> new StepExecutionSleepImpl().step(webDriver, step);
                default -> throw new IllegalArgumentException("Invalid step action: " + action);
            }
        }
    }
}
