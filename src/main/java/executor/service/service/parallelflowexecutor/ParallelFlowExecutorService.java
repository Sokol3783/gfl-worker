package executor.service.service.parallelflowexecutor;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ParallelFlowExecutorService {

    void submit(Thread thread);
    void execute(Thread thread);

}
