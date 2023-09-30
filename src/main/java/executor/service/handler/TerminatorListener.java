package executor.service.handler;

/**
 * The {@code TerminatorListener} functional interface defines a contract for listeners that control
 * the termination of a specific process or operation.
 * <p>
 * Implementing classes or lambda expressions should provide the termination condition by implementing
 * the {@link #shouldTerminate()} method. The method should return {@code true} when the termination condition
 * is met, indicating that the associated process should terminate.
 *
 * @author Yurii Kotsiuba
 */
public interface TerminatorListener {
    /**
     * Checks whether the termination condition is met.
     *
     * @return {@code true} if the termination condition is met, {@code false} otherwise.
     */
    boolean shouldTerminate();
}