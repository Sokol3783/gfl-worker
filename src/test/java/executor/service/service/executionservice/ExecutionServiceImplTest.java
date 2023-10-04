package executor.service.service.executionservice;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.service.scenarios.ScenarioExecutor;
import executor.service.service.webdriver.WebDriverInitializer;
import executor.service.service.webdriver.WebDriverInitializerImpl;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.*;

/**
 * Test class for testing the functionality of the {@code ExecutionServiceImpl} class
 * is an implementation of the {@link ExecutionService}.
 * This class contains unit tests to verify that {@code ExecutionServiceImpl} is working correctly.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see ExecutionService
 * @see Scenario
 * @see ProxyConfigHolder
 */
public class ExecutionServiceImplTest {

    @Test
    public void testWorkMethodExecute() {
        Scenario scenario = new Scenario();
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();

        WebDriver webDriver = mock(WebDriver.class);
        WebDriverInitializer initializer = mock(WebDriverInitializerImpl.class);
        ScenarioExecutor scenarioExecutor = mock(ScenarioExecutor.class);

        doNothing().when(scenarioExecutor).execute(any(Scenario.class), any(WebDriver.class));
        when(initializer.getInstance(proxyConfigHolder)).thenReturn(webDriver);

        ExecutionService executionService = new ExecutionServiceImpl(scenarioExecutor, initializer);
        executionService.execute(scenario, proxyConfigHolder);

        verify(scenarioExecutor, times(1)).execute(any(Scenario.class), any(WebDriver.class));
        verifyNoMoreInteractions(scenarioExecutor);
    }
}
