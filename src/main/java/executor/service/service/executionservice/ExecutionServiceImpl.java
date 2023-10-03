package executor.service.service.executionservice;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.service.scenarios.ScenarioExecutor;
import executor.service.service.webdriver.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

/**
 * Implementation of the {@link ExecutionService} interface.
 * This class orchestrates the execution of scenarios using a provided
 * {@link ScenarioExecutor} and initializes {@link WebDriver} instances using
 * a {@link WebDriverInitializer}.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 * @see executor.service.service.scenarios.ScenarioExecutor
 * @see executor.service.service.webdriver.WebDriverInitializer
 */
public class ExecutionServiceImpl implements ExecutionService {

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverInitializer initializer;

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                WebDriverInitializer initializer) {
        this.scenarioExecutor = scenarioExecutor;
        this.initializer = initializer;
    }

    /**
     * Executes a given scenario using a {@link WebDriver} instance and a
     * provided {@link ProxyConfigHolder}.
     *
     * @param scenario the scenario
     * @param proxy    the proxy
     */
    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriver(proxy);

        scenarioExecutor.execute(scenario, webDriver);
    }

    /**
     * Retrieves a {@link WebDriver} instance using the provided
     * {@link ProxyConfigHolder}.
     *
     * @param proxyConfigHolder The {@link ProxyConfigHolder} entity containing
     *                          proxy configuration information.
     * @return The initialized {@link WebDriver} instance.
     */
    private WebDriver getWebDriver(ProxyConfigHolder proxyConfigHolder) {
        return initializer.getInstance(proxyConfigHolder);
    }
}
