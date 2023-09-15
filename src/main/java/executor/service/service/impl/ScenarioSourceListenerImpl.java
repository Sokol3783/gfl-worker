package executor.service.service.impl;

import executor.service.handler.ScenarioHandler;
import executor.service.handler.TerminatorListener;
import executor.service.model.Scenario;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    private static final long DELAY = 1000;
    private final ScenarioProvider provider;

    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListener.class);

    public ScenarioSourceListenerImpl(ScenarioProvider provider) {
        this.provider = provider;
    }

    @Override
    public void execute(ScenarioHandler handler) {
        execute(handler, () -> false);
    }

    public void execute(ScenarioHandler handler, TerminatorListener terminator) {
        List<Scenario> scenarioList = provider.readScenarios();
        validateScenarios(scenarioList);
        listen(scenarioList, handler, terminator);
    }

    private void validateScenarios(List<Scenario> scenarios) {
        if(scenarios == null || scenarios.isEmpty()) {
            log.error("Bad scenarios list.");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }

    private void listen(List<Scenario> scenarioList, ScenarioHandler handler, TerminatorListener terminator) {
        int currentIndex = 0;
        while(!terminator.shouldTerminate()){
            Scenario scenario = scenarioList.get(currentIndex);
            handler.onScenarioReceived(scenario);
            currentIndex = (currentIndex + 1) % scenarioList.size();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
