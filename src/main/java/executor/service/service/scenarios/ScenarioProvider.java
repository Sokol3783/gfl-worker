package executor.service.service.scenarios;

import executor.service.model.scenario.Scenario;

import java.util.List;

/**
 * The {@code ScenarioProvider} interface defines a contract for classes that provide scenarios.
 * Classes implementing this interface are responsible for reading and providing a list of {@link Scenario} objects.
 * <p>
 * Implementing classes should also handle any exceptions or errors that may occur during the scenario retrieval process.
 *
 * @author Yurii Kotsiuba
 * @see Scenario
 */
public interface ScenarioProvider {
    /**
     * Reads scenarios from a specific source and returns them as a list of {@link Scenario} objects.
     *
     * @return A list of scenarios read from the source.
     */
    List<Scenario> readScenarios();
}