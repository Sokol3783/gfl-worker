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

    private static BlockingQueue<Runnable> queue;

    @Test
    void doNothingWhenEmpty() {
        ContinuousOperations operations = new ContinuousOperationsImpl(new ArrayList<>());
        assertTrue(operations.getContinuousOperations().isEmpty());
        ParallelFlowExecutorService service = getParallelFlow(operations);
        operations.startInterruptedOperation(service, queue);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) service;
        assertEquals(0, poolExecutor.getActiveCount());
    }

    @Test
    void startWhenThreadNull() {
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeWhileTrueOperation()));
        ParallelFlowExecutorService service = getParallelFlow(operations);
        operations.startInterruptedOperation(service, queue);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) service;
        assertEquals(1, poolExecutor.getActiveCount());
    }

    @Test
    void startWhenThreadIsInterrupted() {
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeThrowInterruptedException()));
        ParallelFlowExecutorService service = getParallelFlow(operations);
        operations.startInterruptedOperation(service, queue);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) service;
        assertEquals(1, poolExecutor.getActiveCount());
    }

    @Test
    void startWhenThreadWhenFinished() {
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeExecuteFiveTimes()));
        ParallelFlowExecutorService service = getParallelFlow(operations);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) service;
        operations.startInterruptedOperation(service, queue);
        assertEquals(1, poolExecutor.getActiveCount());
        setSleepUnitTime(6);
        assertEquals(0, poolExecutor.getActiveCount());
        operations.startInterruptedOperation(service, queue);
        assertEquals(1, poolExecutor.getActiveCount());
    }

    @BeforeEach
    void setUp() {
        queue = new LinkedBlockingDeque<>();
    }

    private static ParallelFlowExecutorService getParallelFlow(ContinuousOperations continuousOperations) {
        return new ParallelFlowExecutorServiceImpl(2, 2,
                100, TimeUnit.MILLISECONDS,
                Executors.defaultThreadFactory(), queue,continuousOperations);
    }

    @AfterEach
    void tearDown() {
        queue = null;
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
                        TimeUnit.SECONDS.sleep(10);
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