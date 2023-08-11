package executor.service.service;

import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

public class ExecutionService {

    private WebDriverInitializer driverInitializer;
    private ScenarioExecutor scenarioExecutor;
    private ScenarioSourceListener scenarioSourceListener;

    public ExecutionService(WebDriverInitializer driverInitializer,
                            ScenarioExecutor scenarioExecutor,
                            ScenarioSourceListener scenarioSourceListener) {
        this.driverInitializer = driverInitializer;
        this.scenarioExecutor = scenarioExecutor;
        this.scenarioSourceListener = scenarioSourceListener;
    }

    void execute(Scenario scenario, WebDriver webDriver) {
        WebDriver webDriver = driverInitializer.getInstance();
        Scenario scenario =  scenarioSourceListener.execute();

        if (scenario != null) {
            paralleFlowExecutorService.run(scenario);
        }

    }
}
