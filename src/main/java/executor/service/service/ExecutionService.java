package executor.service.service;

public class ExecutionService {

    private final ScenarioSourceListener scenarioSourceListener;

    public ExecutionService(ScenarioSourceListener scenarioSourceListener) {
        this.scenarioSourceListener = scenarioSourceListener;
    }

    public void execute() {
        scenarioSourceListener.execute();
    }
}
