package executor.service.handler;

import executor.service.model.Scenario;

public interface ScenarioHandler {
    void onScenarioReceived(Scenario scenario);
}
