package executor.service.service.parallelflowexecutor.impls.subscribers;

import executor.service.model.service.ExecutableScenario;

import java.util.List;

public interface ExecutableScenarioComposer {
    List<ExecutableScenario> composeExecutableScenarios();
}
