package executor.service.service.scenario;

import executor.service.handler.ScenarioHandler;
import executor.service.queue.ScenarioQueue;
import executor.service.service.parallelflowexecutor.impls.publishers.ScenarioPublisher;
import executor.service.service.scenarios.ScenarioSourceListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ScenarioProviderTest {
    private ScenarioQueue scenarioQueue;
    private ScenarioSourceListener scenarioListener;
    private ScenarioPublisher scenarioPublisher;

    @BeforeEach
    void setUp() {
        scenarioQueue = mock(ScenarioQueue.class);
        scenarioListener = mock(ScenarioSourceListener.class);
        scenarioPublisher = new ScenarioPublisher(scenarioQueue, scenarioListener);
    }

    @Test
    void testRun() {
        doNothing().when(scenarioListener).execute(any(ScenarioHandler.class));

        scenarioPublisher.run();

        verify(scenarioListener).execute(any(ScenarioHandler.class));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(scenarioListener);
    }
}