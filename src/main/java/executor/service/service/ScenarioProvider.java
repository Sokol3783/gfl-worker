package executor.service.service;

import executor.service.model.Scenario;

import java.util.List;

public interface ScenarioProvider {

    List<Scenario> readScenarios();
}
