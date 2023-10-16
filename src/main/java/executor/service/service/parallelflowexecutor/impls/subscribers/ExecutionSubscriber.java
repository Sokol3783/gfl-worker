package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.service.ExecutableScenario;
import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Operatable;

import java.util.List;

public class ExecutionSubscriber implements Operatable {
    private static final long DELAY = Long.parseLong(System.getProperty("EXECUTION_SUBSCRIBER", "2000"));
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

