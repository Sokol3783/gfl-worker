package executor.service.queue;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;
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
        return scenarios.poll();
    }

    public List<Scenario> getAllScenario() throws InterruptedException {
        List<Scenario> list = new ArrayList<>();
        scenarios.drainTo(list);
        return list;
    }
}