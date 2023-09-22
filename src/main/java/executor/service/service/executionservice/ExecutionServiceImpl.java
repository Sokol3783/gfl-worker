package executor.service.service.executionservice;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.service.scenarios.ScenarioExecutor;
import executor.service.service.webdriver.WebDriverInitializer;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionServiceImpl implements ExecutionService {
    private static final Logger log = LoggerFactory.getLogger(ExecutionServiceImpl.class);

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverInitializer initializer;

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor, WebDriverInitializer initializer) {
        this.scenarioExecutor = scenarioExecutor;
        this.initializer = initializer;
    }

    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxy) {
        WebDriver webDriver = getWebDriver(proxy);
        if (webDriver == null) {
            log.error("Web-driver initialization error");
            return;
        }
        scenarioExecutor.execute(scenario, webDriver);
    }

    private WebDriver getWebDriver(ProxyConfigHolder proxyConfigHolder) {
        return initializer.getInstance(proxyConfigHolder);
    }
}