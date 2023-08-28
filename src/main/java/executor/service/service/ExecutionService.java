package executor.service.service;

import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ExecutionService {

    private final ScenarioSourceListener scenarioSourceListener;
    private final ScenarioExecutor scenarioExecutor;
    public ExecutionService(ScenarioSourceListener scenarioSourceListener,
                            ScenarioExecutor scenarioExecutor) {
        this.scenarioSourceListener = scenarioSourceListener;
        this.scenarioExecutor = scenarioExecutor;
    }

    public void execute() {
        WebDriver webDriver = getWebDriverPrototype();

        List<Scenario> scenarios = scenarioSourceListener.execute();

        scenarios.forEach(scenario -> scenarioExecutor.execute(scenario, webDriver));
    }

    private WebDriver getWebDriverPrototype() {
        WebDriverInitializer webDriverInitializer = new WebDriverInitializer();
        return webDriverInitializer.getInstance();
    }
}
