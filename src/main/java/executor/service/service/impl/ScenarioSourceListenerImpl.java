package executor.service.service.impl;

import executor.service.handler.ScenarioHandler;
import executor.service.handler.TerminatorListener;
import executor.service.model.Scenario;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The {@code ScenarioSourceListenerImpl} class is an implementation of the {@link ScenarioSourceListener}
 * interface. It listens for scenarios from a {@link ScenarioProvider} and processes them using a provided
 * {@link ScenarioHandler}. The scenarios are continuously processed in a loop with a specified delay.
 *  * </pre>
 *  * Alternatively, you can use the overloaded execute method with a {@link TerminatorListener} to control
 *  * the termination of the listening process.
 *  * <pre>
 * @author Yurii Kotsiuba
 * @see ScenarioSourceListener
 * @see ScenarioProvider
 * @see ScenarioHandler
 */
public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    private static final long DELAY = 1000;
    private final ScenarioProvider provider;

    private static final Logger log = LoggerFactory.getLogger(ScenarioSourceListener.class);

    /**
     * Constructs a new {@code ScenarioSourceListenerImpl} with the specified scenario provider.
     *
     * @param provider The provider responsible for reading scenarios.
     */
    public ScenarioSourceListenerImpl(ScenarioProvider provider) {
        this.provider = provider;
    }

    /**
     * Executes the scenario listening process with a default terminator condition that always returns false.
     * It reads scenarios from the provider, validates them, and continuously listens for scenarios using the
     * provided handler.
     *
     * @param handler The scenario handler responsible for processing received scenarios.
     * @see #execute(ScenarioHandler, TerminatorListener)
     */
    @Override
    public void execute(ScenarioHandler handler) {
        execute(handler, () -> false);
    }

    /**
     * Executes the scenario listening process with the option to specify a custom terminator condition.
     * It reads scenarios from the provider, validates them, and continuously listens for scenarios using the
     * provided handler. The listening process can be terminated by the custom terminator condition.
     *
     * @param handler    The scenario handler responsible for processing received scenarios.
     * @param terminator The terminator listener to control the termination of the listening process.
     */
    public void execute(ScenarioHandler handler, TerminatorListener terminator) {
        List<Scenario> scenarioList = provider.readScenarios();
        validateScenarios(scenarioList);
        listen(scenarioList, handler, terminator);
    }

    /**
     * Validates the list of scenarios to ensure it is not null or empty. If the list
     * is invalid, an error is logged, and an {@link IllegalArgumentException} is thrown.
     *
     * @param scenarios The list of scenarios to validate.
     * @throws IllegalArgumentException if the list is null or empty.
     */
    private void validateScenarios(List<Scenario> scenarios) {
        if(scenarios == null || scenarios.isEmpty()) {
            log.error("Bad scenarios list.");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }

    /**
     * Listens for scenarios in a loop and passes them to the provided handler. The scenarios
     * are processed continuously with a delay between each processing cycle. The listening process
     * can be terminated based on the provided terminator condition.
     *
     * @param scenarioList The list of scenarios to listen to.
     * @param handler      The scenario handler responsible for processing received scenarios.
     * @param terminator   The terminator listener to control the termination of the listening process.
     */
    private void listen(List<Scenario> scenarioList, ScenarioHandler handler, TerminatorListener terminator) {
        int currentIndex = 0;
        while(!terminator.shouldTerminate()){
            Scenario scenario = scenarioList.get(currentIndex);
            handler.onScenarioReceived(scenario);
            currentIndex = (currentIndex + 1) % scenarioList.size();
            sleep();
        }
    }

    /**
     * Sleeps for a specified delay, allowing for a controlled pace of scenario processing.
     */
    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}