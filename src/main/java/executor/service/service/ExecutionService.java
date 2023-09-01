package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.impl.WebDriverInitializerImpl;
import org.openqa.selenium.WebDriver;

import java.util.Queue;

/**
 * The facade for execute ScenarioExecutor.
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class ExecutionService {

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverConfig webDriverConfig;

    public ExecutionService(ScenarioExecutor scenarioExecutor,
                            WebDriverConfig webDriverConfig) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverConfig = webDriverConfig;
    }

    /**
     * Execute ScenarioExecutor.
     *
     * @param scenarios the queue with scenarios
     * @param proxies   the queue with proxies
     */
    public void execute(Queue<Scenario> scenarios, Queue<ProxyConfigHolder> proxies) {
        for (Scenario scenario : scenarios) {
            ProxyConfigHolder proxy = proxies.poll();
            if (scenario == null || proxy == null) continue;

            WebDriver webDriver = getWebDriverPrototype(webDriverConfig, proxy);
            if (webDriver == null) continue;

            scenarioExecutor.execute(scenario, webDriver);
        }
    }

    /**
     * Get WebDriver as Prototype.
     *
     * @param webDriverConfig   the WebDriverConfig entity
     * @param proxy the ProxyConfigHolder entity
     */
    private WebDriver getWebDriverPrototype(WebDriverConfig webDriverConfig, ProxyConfigHolder proxy) {
        return new WebDriverInitializerImpl().getInstance(webDriverConfig, proxy);
    }
}
