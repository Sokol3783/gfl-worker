package executor.service.queue;

import executor.service.model.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ScenarioQueue {
    private final BlockingQueue<Scenario> scenarios;

    public ScenarioQueue() {
        this.scenarios = new ArrayBlockingQueue<>(5000, true);
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
    }

    public Scenario getScenario(){
        return scenarios.poll();
    }

    public List<Scenario> getAllScenario() {
        List<Scenario> list = new ArrayList<>();
        scenarios.drainTo(list);
        return list;
    }
}