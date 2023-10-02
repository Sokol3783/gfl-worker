package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Task;

import java.util.List;

public class ExecutionSubscriber implements Task {
    private static final long DELAY = 2000L;
    private final ExecutionService executionService;
    private final ParallelFlowExecutorService parallelFlow;
    private final PairGeneratorService pairGenerator;

    public ExecutionSubscriber(ExecutionService executionService,
                               ParallelFlowExecutorService parallelFlow,
                               PairGeneratorService pairGenerator) {
        this.executionService = executionService;
        this.parallelFlow = parallelFlow;
        this.pairGenerator = pairGenerator;
    }

    @Override
    public void run() {
        while (true) {
            List<Pair> pairs = pairGenerator.generatePairs();
            pairs.forEach(pair ->
                    parallelFlow.execute(() -> executionService.execute(pair.getScenario(), pair.getProxy())));
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

