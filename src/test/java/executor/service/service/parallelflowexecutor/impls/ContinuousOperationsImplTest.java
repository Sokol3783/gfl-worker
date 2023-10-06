package executor.service.service.parallelflowexecutor.impls;

import executor.service.model.service.ContinuousOperationNode;
import executor.service.service.parallelflowexecutor.ContinuousOperations;
import executor.service.service.parallelflowexecutor.Operatable;
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

    @Test
    void doNothingWhenEmpty() {
        ContinuousOperations operations = new ContinuousOperationsImpl(new ArrayList<>(), factory);
        assertTrue(operations.getContinuousOperations().isEmpty());
        operations.startInterruptedOperation();
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        assertEquals(0, poolExecutor.getActiveCount());
    }

    @Test
    void startWhenThreadNull() {
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeWhileTrueOperation()), factory);
        operations.startInterruptedOperation();
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        assertEquals(1, poolExecutor.getQueue().size());
    }

    @Test
    void startWhenThreadIsInterrupted() {
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeThrowInterruptedException()), factory);
        operations.startInterruptedOperation();
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        assertEquals(1, poolExecutor.getQueue().size());
    }

    @Test
    void startWhenThreadWhenFinished() {
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
        ContinuousOperations operations = new ContinuousOperationsImpl(List.of(getOperatableNodeExecuteFiveTimes()), factory);
        operations.startInterruptedOperation();
        assertEquals(1, poolExecutor.getQueue().size());
        setSleepUnitTime(6);
        assertEquals(0, poolExecutor.getQueue().size());
        operations.startInterruptedOperation();
        assertEquals(1, poolExecutor.getQueue().size());
    }

    @BeforeEach
    void setUp() {
        factory = Executors.defaultThreadFactory();
        executor = Executors.newFixedThreadPool(2, factory);
    }

    @AfterEach
    void tearDown() {
        factory = null;
    }

    private ContinuousOperationNode getOperatableNodeExecuteFiveTimes() {
        return new ContinuousOperationNode(new Operatable() {

            int[] count = new int[5];
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
            private static final CountDownLatch START = new CountDownLatch(3);
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