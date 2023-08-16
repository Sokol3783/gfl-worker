package executor.service.service;

import executor.service.model.Scenario;
import executor.service.model.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ParalleFlowExecutorServiceTest {

    private ScenarioExecutor scenarioExecutor;
    private WebDriver webDriver;
    private ParalleFlowExecutorService parallelFlow;

    @BeforeEach
    void setUp() {
        this.scenarioExecutor = mock(ScenarioExecutor.class);
        this.webDriver = mock(WebDriver.class);
        this.parallelFlow = new ParalleFlowExecutorService(scenarioExecutor);
    }

    @AfterEach
    void tearDown() {
        parallelFlow.shutdown();
    }

    @Test
    public void test() {
        List<Step> steps1 = new ArrayList<>();
        steps1.add(new Step("clickCss", "body > ul > li:nth-child(1) > a"));
        steps1.add(new Step("sleep", "5:10"));
        steps1.add(new Step("clickXpath", "/html/body/p"));

        var scenario1 = new Scenario(
                "test scenario 1",
                "http://info.cern.ch",
                steps1
        );

        List<Step> steps2 = new ArrayList<>();
        steps2.add(new Step("clickXpath", "/html/body/p"));
        steps2.add(new Step("sleep", "5:10"));
        steps2.add(new Step("clickCss", "body > ul > li:nth-child(1) > a"));

        var scenario2 = new Scenario(
                "test scenario 2",
                "http://info.cern.ch",
                steps2
        );

        var arrScenario = new Scenario[] {scenario1,scenario2};

        for (Scenario scenario : arrScenario) {
            parallelFlow.enqueueScenarioForExecution(scenario, webDriver);
        }

        verify(scenarioExecutor, times(2)).execute(any(), eq(webDriver));
    }
}
