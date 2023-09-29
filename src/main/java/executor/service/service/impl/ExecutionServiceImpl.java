package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.StepExecution;
import executor.service.service.StepExecutionFabric;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * The facade for execute ScenarioExecutor.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ExecutionServiceImpl implements ExecutionService {

    private final StepExecutionFabric stepExecutionFabric;
    private final WebDriverConfig webDriverConfig;

    public ExecutionServiceImpl(StepExecutionFabric stepExecutionFabric,
                                WebDriverConfig webDriverConfig) {
        this.stepExecutionFabric = stepExecutionFabric;
        this.webDriverConfig = webDriverConfig;
    }

    /**
     * Execute ScenarioExecutor.
     *
     * @param scenario the scenario
     * @param proxy    the proxy
     */
    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriverPrototype(webDriverConfig, proxy);
        webDriver.get(scenario.getSite());

        List<Step> steps = scenario.getSteps();
        StepExecution stepExecutor;
        for (Step step : steps) {
            stepExecutor = stepExecutionFabric.getStepExecutor(step.getAction().getName());
            stepExecutor.step(webDriver, step);
        }
    }

    /**
     * Get WebDriver as Prototype.
     *
     * @param webDriverConfig   the WebDriverConfig entity
     * @param proxyConfigHolder the ProxyConfigHolder entity
     */
    private WebDriver getWebDriverPrototype(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        WebDriverInitializer webDriverInitializer = new WebDriverInitializerImpl();
        return webDriverInitializer.getInstance(webDriverConfig, proxyConfigHolder);
    }
}
