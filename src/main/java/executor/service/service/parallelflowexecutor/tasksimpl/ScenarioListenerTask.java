package executor.service.service.parallelflowexecutor.tasksimpl;

import executor.service.queue.ScenarioQueue;
import executor.service.service.parallelflowexecutor.Task;
import executor.service.service.scenarios.ScenarioSourceListener;

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
