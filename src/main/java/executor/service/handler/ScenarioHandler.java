package executor.service.handler;

import executor.service.model.scenario.Scenario;

public interface ScenarioHandler {
    void onScenarioReceived(Scenario scenario);
}
