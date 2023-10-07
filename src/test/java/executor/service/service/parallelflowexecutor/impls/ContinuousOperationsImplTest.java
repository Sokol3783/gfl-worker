package executor.service.service.parallelflowexecutor.impls;

import executor.service.model.service.ContinuousOperationNode;
import executor.service.service.parallelflowexecutor.ContinuousOperations;
import executor.service.service.parallelflowexecutor.Operatable;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ContinuousOperationsImplTest {

    private static ThreadFactory factory;
    private static ExecutorService executor;

    private static BlockingQueue<Runnable> queue;

    @Test
    void doNothingWhenEmpty() {
        ContinuousOperations operations = new ContinuousOperationsImpl(new ArrayList<>(), factory);
        assertTrue(operations.getContinuousOperations().isEmpty());
        ParallelFlowExecutorService service = getParallelFlow(operations);
        operations.startInterruptedOperation(service, queue);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        assertEquals(0, poolExecutor.getActiveCount());
    }

    @Test
    void startWhenThreadNull() {
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeWhileTrueOperation()), factory);
        ParallelFlowExecutorService service = getParallelFlow(operations);
        operations.startInterruptedOperation(service, queue);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        assertEquals(1, poolExecutor.getQueue().size());
    }

    @Test
    void startWhenThreadIsInterrupted() {
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeThrowInterruptedException()), factory);
        ParallelFlowExecutorService service = getParallelFlow(operations);
        operations.startInterruptedOperation(service, queue);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        assertEquals(1, poolExecutor.getQueue().size());
    }

    @Test
    void startWhenThreadWhenFinished() {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeExecuteFiveTimes()), factory);
        ParallelFlowExecutorService service = getParallelFlow(operations);
        operations.startInterruptedOperation(service, queue);
        assertEquals(1, poolExecutor.getQueue().size());
        setSleepUnitTime(6);
        assertEquals(0, poolExecutor.getQueue().size());
        operations.startInterruptedOperation(service, queue);
        assertEquals(1, poolExecutor.getQueue().size());
    }

    @BeforeEach
    void setUp() {
        factory = Executors.defaultThreadFactory();
        executor = Executors.newFixedThreadPool(2, factory);
        queue = new LinkedBlockingDeque<>();
    }

    private static ParallelFlowExecutorService getParallelFlow(ContinuousOperations continuousOperations) {
        return new ParallelFlowExecutorServiceImpl(2, 2,
                100, TimeUnit.MILLISECONDS,
                factory, queue,continuousOperations);
    }

    @AfterEach
    void tearDown() {
        factory = null;
    }

    private ContinuousOperationNode getOperatableNodeExecuteFiveTimes() {
        return new ContinuousOperationNode(new Operatable() {

            final int[] count = new int[5];
            @Override
            public void run() {
                while (count[0] > 0) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                count[0]--;
            }
        });
    }

    private ContinuousOperationNode getOperatableNodeThrowInterruptedException() {
        return new ContinuousOperationNode(new Operatable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        throw  new InterruptedException("Error!");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private ContinuousOperationNode getOperatableNodeWhileTrueOperation() {
        return new ContinuousOperationNode(new Operatable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void setSleepUnitTime(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}