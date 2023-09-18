package executor.service.service.impl;

import executor.service.model.ScenarioQueue;
import executor.service.service.ScenarioSourceListener;

public class ScenarioPublisher implements Runnable {
    private final ScenarioQueue scenarioQueue;
    private final ScenarioSourceListener listener;

    public ScenarioPublisher(ScenarioQueue scenarioQueue, ScenarioSourceListener listener) {
        this.scenarioQueue = scenarioQueue;
        this.listener = listener;
    }

    @Override
    public void run() {
        listener.execute(scenarioQueue::addScenario);
    }
}
