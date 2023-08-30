package executor.service.service.impl;

import executor.service.model.Scenario;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    public static final int DELAY = 1;
    private final ScenarioProvider provider;

    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListener.class);

    public ScenarioSourceListenerImpl(ScenarioProvider provider) {
        this.provider = provider;
    }

    @Override
    public Flux<Scenario> getScenarios() {
        List<Scenario> scenarios = provider.readScenarios();
        validateScenarios(scenarios);
        return Flux.fromIterable(scenarios)
                .log()
                .delayElements(Duration.ofSeconds(DELAY))
                .repeat();
    }

    private void validateScenarios(List<Scenario> scenarios) {
        if(scenarios == null || scenarios.isEmpty()) {
            log.error("Bad scenarios list.");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }
}
