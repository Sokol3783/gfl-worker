package executor.service.util;

import executor.service.handler.TerminatorListener;

public class TestTerminator implements TerminatorListener {
    private int maxIterations;
    private int currentIteration = 0;

    public TestTerminator(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public boolean shouldTerminate() {
        currentIteration++;
        return currentIteration > maxIterations;
    }
}