package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.WebDriverConfig;
import executor.service.service.ExecutionService;
import executor.service.service.ScenarioExecutor;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * The facade for execute ScenarioExecutor.
 *
 *  @author Oleksandr Tuleninov
 *  @version 01
 * */
public class ExecutionServiceImpl implements ExecutionService {

    private static final Logger log = LoggerFactory.getLogger(ExecutionServiceImpl.class);

    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverConfig webDriverConfig;
    private final ProxyConfigHolder defaultProxy;

    public ExecutionServiceImpl(ScenarioExecutor scenarioExecutor,
                                WebDriverConfig webDriverConfig, ProxyConfigHolder defaultProxy) {
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverConfig = webDriverConfig;
        this.defaultProxy = defaultProxy;
    }

    /**
     * Execute ScenarioExecutor.
     *
     * @param scenarios the queue with scenarios
     * @param proxies   the queue with proxies
     */
    @Override
    public void execute(BlockingQueue<Scenario> scenarios,
                        BlockingQueue<ProxyConfigHolder> proxies) {
        ProxyConfigHolder proxy = defaultProxy;
        while (true) {
            try {
                Scenario scenario = scenarios.take();

                ProxyConfigHolder newProxy = proxies.poll();
                if (newProxy != null) {
                    proxy = newProxy;
                }

                WebDriver webDriver = getWebDriverPrototype(webDriverConfig, proxy);
                if (webDriver == null) continue;

                scenarioExecutor.execute(scenario, webDriver);
            } catch (InterruptedException e) {
                log.info("Thread was interrupted in ExecutionServiceImpl.class");
                Thread.currentThread().interrupt();
            }
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
