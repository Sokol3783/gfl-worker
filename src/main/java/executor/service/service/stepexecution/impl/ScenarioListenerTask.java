package executor.service.service.impl;

import executor.service.model.ScenarioQueue;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.Task;

public class ScenarioListenerTask implements Task {
    private final ScenarioQueue scenarioQueue;
    private final ScenarioSourceListener listener;

    public ScenarioListenerTask(ScenarioQueue scenarioQueue, ScenarioSourceListener listener) {
        this.scenarioQueue = scenarioQueue;
        this.listener = listener;
    }

    @Override
    public void run() {
        listener.execute(scenarioQueue::addScenario);
    }
}
