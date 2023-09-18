package executor.service.model;

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