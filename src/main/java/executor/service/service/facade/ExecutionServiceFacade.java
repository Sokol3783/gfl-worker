package executor.service.service.facade;

import executor.service.model.Scenario;
import executor.service.service.ScenarioExecutor;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

public class ExecutionServiceFacade {

    private WebDriverInitializer driverInitializer;
    private ScenarioExecutor scenarioExecutor;
    private ScenarioSourceListener scenarioSourceListener;

    public ExecutionServiceFacade(WebDriverInitializer driverInitializer, ScenarioExecutor scenarioExecutor, ScenarioSourceListener scenarioSourceListener) {
        this.driverInitializer = driverInitializer;
        this.scenarioExecutor = scenarioExecutor;
        this.scenarioSourceListener = scenarioSourceListener;
    }

    public void executeScenario() {
        WebDriver webDriver = driverInitializer.getInstance();
        Scenario scenario =  scenarioSourceListener.execute();

        if (scenario != null) {
            scenarioExecutor.execute(scenario, webDriver);
        }
    }
}
