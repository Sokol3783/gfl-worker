package executor.service.service.scenarios;

import executor.service.handler.ScenarioHandler;

/**
 * The {@code ScenarioSourceListener} interface defines a contract for listening to scenarios and
 * executing actions on received scenarios.
 * <p>
 * Implementing classes or objects that implement this interface are responsible for executing
 * the specified {@link #execute(ScenarioHandler)} method when scenarios are available for processing.
 * The actual behavior of handling scenarios may vary based on the implementation.
 * <p>
 * @author Yurii Kotsiuba
 * @see ScenarioHandler
 */
public interface ScenarioSourceListener {

    /**
     * Executes an action on received scenarios using the provided {@link ScenarioHandler}.
     * The specific behavior of handling scenarios is determined by the implementing class.
     *
     * @param handler The scenario handler responsible for processing received scenarios.
     */
    void execute(ScenarioHandler handler);

}