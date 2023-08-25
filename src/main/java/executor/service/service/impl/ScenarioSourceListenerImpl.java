package executor.service.service.impl;

import executor.service.model.Step;
import executor.service.service.ScenarioSourceListener;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    private Step step;

    public ScenarioSourceListenerImpl() {
    }

    public ScenarioSourceListenerImpl(Step step) {
        this.step = step;
    }

    @Override
    public void execute() {

    }
}
