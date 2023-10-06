package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Task;

import java.util.List;

public class ExecutionSubscriber implements Task {
    private static final long DELAY = 2000L;
    private final ExecutionService executionService;
    private final ParallelFlowExecutorService parallelFlow;
    private final ExecutableScenarioComposer scenarioComposer;

    public ExecutionSubscriber(ExecutionService executionService,
                               ParallelFlowExecutorService parallelFlow,
                               ExecutableScenarioComposer scenarioComposer) {
        this.executionService = executionService;
        this.parallelFlow = parallelFlow;
        this.scenarioComposer = scenarioComposer;
    }

    @Override
    public void run() {
        while (true) {
            List<ExecutableScenario> executableScenarios = scenarioComposer.composeExecutableScenarios();
            executableScenarios.forEach(executableScenario ->
                    parallelFlow.execute(() -> executionService.execute(executableScenario.scenario(), executableScenario.proxy())));
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

