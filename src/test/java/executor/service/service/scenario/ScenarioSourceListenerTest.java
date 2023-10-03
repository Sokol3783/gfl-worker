package executor.service.service.scenario;

import executor.service.handler.ScenarioHandler;
import executor.service.handler.TerminatorListener;
import executor.service.model.scenario.Scenario;
import executor.service.service.scenarios.ScenarioProvider;
import executor.service.service.scenarios.impl.ScenarioSourceListenerImpl;
import executor.service.util.TestTerminator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ScenarioSourceListenerTest {

    private ScenarioSourceListenerImpl listener;

    private ScenarioProvider provider;

    private ScenarioHandler handler;

    @BeforeEach
    public void setUp() {
        provider = mock(ScenarioProvider.class);
        handler = mock(ScenarioHandler.class);
        listener = new ScenarioSourceListenerImpl(provider);
    }

    @Test
    public void testExecute_numberOfTimes() {
        int maxIterations = 5;
        TerminatorListener terminator = new TestTerminator(maxIterations);

        given(provider.readScenarios()).willReturn(prepareScenarioList());

        listener.execute(handler, terminator);

        verify(handler, times(maxIterations)).onScenarioReceived(any(Scenario.class));
        verify(provider).readScenarios();
    }

    @Test
    public void testExecute_throwException_whenNullList() {
        given(provider.readScenarios()).willReturn(null);

        assertThrows(IllegalArgumentException.class, ()-> listener.execute(handler, () -> false));
        verify(provider).readScenarios();
    }

    @Test
    public void testExecute_throwException_whenEmptyList() {
        List<Scenario> emptyScenarioList = new ArrayList<>();

        given(provider.readScenarios()).willReturn(emptyScenarioList);

        assertThrows(IllegalArgumentException.class, ()-> listener.execute(handler, () -> false));
        verify(provider).readScenarios();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(provider);
        verifyNoMoreInteractions(handler);
    }

    private List<Scenario> prepareScenarioList() {
        return Arrays.asList(
                new Scenario(),
                new Scenario(),
                new Scenario()
        );
    }
}