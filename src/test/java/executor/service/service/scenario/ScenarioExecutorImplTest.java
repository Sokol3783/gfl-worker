package executor.service.service.scenario;

import executor.service.model.scenario.Scenario;
import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepAction;
import executor.service.service.scenarios.ScenarioExecutor;
import executor.service.service.scenarios.impl.ScenarioExecutorImpl;
import executor.service.service.stepexecution.StepExecution;
import executor.service.service.stepexecution.StepExecutionFabric;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ScenarioExecutorImpl} class
 * is an implementation of the {@link ScenarioExecutor}.
 * This class contains unit tests to verify that {@code ScenarioExecutorImpl} is working correctly.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see StepExecutionFabric
 * @see Scenario
 */
public class ScenarioExecutorImplTest {

    private StepExecutionFabric stepExecutionFabric;

    @BeforeEach
    public void setUp() {
        this.stepExecutionFabric = mock(StepExecutionFabric.class);
    }

    @AfterEach
    void tearDown() {
        this.stepExecutionFabric = null;
    }

    @Test
    public void testWorkMethodExecute() {
        Scenario scenario = getScenario();
        WebDriver webDriver = mock(WebDriver.class);
        StepExecution stepExecution = mock(StepExecution.class);

        when(stepExecutionFabric.getStepExecutor(anyString())).thenReturn(stepExecution);
        doNothing().when(stepExecution).step(any(WebDriver.class), any(Step.class));

        ScenarioExecutor scenarioExecutor = new ScenarioExecutorImpl(stepExecutionFabric);
        scenarioExecutor.execute(scenario, webDriver);

        verify(stepExecution, times(numberOfSteps())).step(any(), any(Step.class));
    }

    private int numberOfSteps() {
        return listOfSteps().size();
    }

    private List<Step> listOfSteps() {
        return getScenario().getSteps();
    }

    private Scenario getScenario() {
        return new Scenario(
                "test scenario 1",
                "http://info.cern.ch",
                Arrays.asList(
                        new Step(StepAction.CLICK_CSS, "body > ul > li:nth-child(1) > a"),
                        new Step(StepAction.SLEEP, "5000:15000"),
                        new Step(StepAction.CLICK_XPATH, "/html/body/p")));
    }
}
