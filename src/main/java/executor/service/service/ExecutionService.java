package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.impl.WebDriverInitializerImpl;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Queue;

public class ExecutionService {

    private static final Logger log = LoggerFactory.getLogger(ExecutionService.class);


    private final ScenarioSourceListener scenarioSourceListener;
    private final ScenarioExecutor scenarioExecutor;
    public ExecutionService(ScenarioSourceListener scenarioSourceListener,
                            ScenarioExecutor scenarioExecutor) {
        this.scenarioSourceListener = scenarioSourceListener;
        this.scenarioExecutor = scenarioExecutor;
    }

    public void execute(Queue<Scenario> scenarios, Queue<ProxyConfigHolder> proxies) {
        // todo read data for WebDriverConfig()

        WebDriver webDriver = getWebDriverPrototype();

        checkingWebDriverForNull(webDriver);

        scenarioExecutor.execute(new Scenario(), webDriver);
    }

    private void checkingWebDriverForNull(WebDriver webDriver) {
        if (webDriver == null) {
            log.error("WebDriver cannot be null");
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
    }

    private WebDriver getWebDriverPrototype() {
        WebDriverInitializer webDriverInitializer = new WebDriverInitializerImpl();
        return webDriverInitializer.getInstance(new WebDriverConfig(), new ProxyConfigHolder());
    }
}
