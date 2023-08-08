package executor.service.service;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;

public class ParalleFlowExecutorService {

    private final WebDriver webDriver;
    private final ExecutionService executionService;
    private final BlockingQueue<Runnable> blockingQueue;

    public ParalleFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
        blockingQueue = new LinkedBlockingQueue<>();
    }

    public void addScenario(Scenario scenario) {
        blockingQueue.add(() -> executionService.execute(scenario, webDriver));
    }

    public void runAllScenario() {
        ThreadPoolConfig threadPoolConfig = createThreadPoolConfig();

        var threadPool = new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                16,
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.MINUTES,
                blockingQueue
        );
        threadPool.shutdown();
    }

    private ThreadPoolConfig createThreadPoolConfig() {
        var properties = new PropertiesConfig().getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));

        return new ThreadPoolConfig(corePoolSize, keepAliveTime);
    }
}
