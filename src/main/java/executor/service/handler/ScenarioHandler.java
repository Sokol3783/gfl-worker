package executor.service.handler;

import executor.service.model.scenario.Scenario;

/**
 * The {@code ScenarioHandler} functional interface defines a contract for handling scenarios received
 * during a specific process or operation.
 * <p>
 * Implementing classes or lambda expressions should provide the logic for processing received scenarios
 * by implementing the {@link #onScenarioReceived(Scenario)} method.
 *
 * @author Yurii Kotsiuba
 */
public interface ScenarioHandler {
    /**
     * Handles a received scenario.
     *
     * @param scenario The scenario received for processing.
     */
    void onScenarioReceived(Scenario scenario);
}