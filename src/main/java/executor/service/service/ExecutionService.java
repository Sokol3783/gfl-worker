package executor.service.service;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExecutionService {

    private static final Logger log = LoggerFactory.getLogger(ExecutionService.class);


    private final ScenarioSourceListener scenarioSourceListener;
    private final ScenarioExecutor scenarioExecutor;
    public ExecutionService(ScenarioSourceListener scenarioSourceListener,
                            ScenarioExecutor scenarioExecutor) {
        this.scenarioSourceListener = scenarioSourceListener;
        this.scenarioExecutor = scenarioExecutor;
    }

    public void execute() {
        WebDriver webDriver = getWebDriverPrototype();

        checkingWebDriverForNull(webDriver);

        List<Scenario> scenarios = scenarioSourceListener.execute();

        scenarios.forEach(scenario -> scenarioExecutor.execute(scenario, webDriver));
    }

    private void checkingWebDriverForNull(WebDriver webDriver) {
        if (webDriver == null) {
            log.error("WebDriver cannot be null");
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
    }

    private WebDriver getWebDriverPrototype() {
        WebDriverInitializer webDriverInitializer = new WebDriverInitializer();
        return webDriverInitializer.getInstance();
    }
}
