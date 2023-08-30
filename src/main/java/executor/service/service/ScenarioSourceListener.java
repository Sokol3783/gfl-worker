package executor.service.service;

import executor.service.model.Scenario;
import reactor.core.publisher.Flux;

public interface ScenarioSourceListener {

    Flux<Scenario> getScenarios();

}
