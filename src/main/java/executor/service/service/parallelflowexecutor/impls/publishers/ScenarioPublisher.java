package executor.service.service.parallelflowexecutor.impls.publishers;


import executor.service.queue.ScenarioQueue;
import executor.service.service.parallelflowexecutor.Task;
import executor.service.service.scenarios.ScenarioSourceListener;

/**
 * The {@code ScenarioPublisher} class is responsible for publishing scenarios to a scenario queue
 * by utilizing a provided {@link ScenarioSourceListener}.
 * <p>
 * This class implements the {@link Task} interface, allowing it to be executed in a separate
 * thread or context.
 * <p>
 *
 * @author Yurii Kotsiuba
 * @see Task
 * @see ScenarioQueue
 * @see ScenarioSourceListener
 */
public class ScenarioPublisher implements Task {
    private final ScenarioQueue scenarioQueue;
    private final ScenarioSourceListener listener;

    /**
     * Constructs a new {@code ScenarioPublisher} with the specified scenario queue and listener.
     *
     * @param scenarioQueue The scenario queue to which scenarios will be published.
     * @param listener      The listener responsible for executing scenario publication.
     */
    public ScenarioPublisher(ScenarioQueue scenarioQueue, ScenarioSourceListener listener) {
        this.scenarioQueue = scenarioQueue;
        this.listener = listener;
    }

    /**
     * Runs the scenario publication process by invoking the execute
     * method with the scenario queue's {@code addScenario} method reference.
     */
    @Override
    public void run() {
        listener.execute(scenarioQueue::addScenario);
    }
}
