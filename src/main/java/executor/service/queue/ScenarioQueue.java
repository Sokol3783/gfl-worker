package executor.service.queue;

import executor.service.model.scenario.Scenario;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ScenarioQueue {
    private BlockingQueue<Scenario> scenarios;

    public ScenarioQueue() {
        this.scenarios = new LinkedBlockingDeque<>();
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
    }

    public Scenario getScenario() throws InterruptedException {
        return scenarios.take();
    }
}