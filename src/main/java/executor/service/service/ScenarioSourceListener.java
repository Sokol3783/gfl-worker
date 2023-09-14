package executor.service.service;

import executor.service.handler.ScenarioHandler;
import executor.service.model.Scenario;
import reactor.core.publisher.Flux;

public interface ScenarioSourceListener {

    void execute(ScenarioHandler handler);

}
