package executor.service.service;

public class ExecutionService {

    private WebDriverInitializer driverInitializer;
    private ScenarioExecutor scenarioExecutor;
    private ScenarioSourceListener scenarioSourceListener;

    public ExecutionService(WebDriverInitializer driverInitializer, ScenarioExecutor scenarioExecutor, ScenarioSourceListener scenarioSourceListener) {
        this.driverInitializer = driverInitializer;
        this.scenarioExecutor = scenarioExecutor;
        this.scenarioSourceListener = scenarioSourceListener;
    }

    public void execute() {

        scenarioSourceListener.execute();

    }
}
