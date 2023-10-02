package executor.service.service.parallelflowexecutor.impls.publishers;


import executor.service.queue.ScenarioQueue;
import executor.service.service.parallelflowexecutor.Operatable;
import executor.service.service.scenarios.ScenarioSourceListener;

public class ScenarioPublisher implements Operatable {
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
