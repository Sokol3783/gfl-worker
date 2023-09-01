package executor.service.service;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import reactor.core.publisher.Flux;

import java.util.Queue;
import java.util.concurrent.*;

import static executor.service.config.properties.PropertiesConstants.*;

/**
 * Start ExecutionService in parallel multi-threaded mode.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class ParalleFlowExecutorService {

    private static final Queue<Scenario> SCENARIO_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<ProxyConfigHolder> PROXY_QUEUE = new ConcurrentLinkedQueue<>();
    private static final int NUMBER_TIMES = 3;
    private static final CountDownLatch CDL = new CountDownLatch(NUMBER_TIMES);
    private static final int DELAY = 3;

    private ExecutionService service;
    private ScenarioSourceListener scenarioSourceListener;
    private ProxySourcesClient proxySourcesClient;
    private PropertiesConfig propertiesConfig;
    private ThreadPoolConfig threadPoolConfig;

    public ParalleFlowExecutorService() {
    }

    public ParalleFlowExecutorService(ExecutionService service,
                                      ScenarioSourceListener scenarioSourceListener,
                                      ProxySourcesClient proxySourcesClient,
                                      PropertiesConfig propertiesConfig,
                                      ThreadPoolConfig threadPoolConfig) {
        this.service = service;
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.propertiesConfig = propertiesConfig;
        this.threadPoolConfig = threadPoolConfig;
    }

    /**
     * Start ScenarioSourceListener, ProxySourcesClient, ExecutionService
     * in parallel multi-threaded mode.
     */
    public void execute() {
        configureThreadPoolConfig(propertiesConfig, threadPoolConfig);
        ExecutorService threadPoolExecutor = createThreadPoolExecutor(threadPoolConfig);

        threadPoolExecutor.execute(getScenariosRunnable());

        threadPoolExecutor.execute(getProxiesRunnable());

        threadPoolExecutor.execute(getExecutionServiceRunnable());

        await();
        threadPoolExecutor.shutdown();
    }

    private Runnable getScenariosRunnable() {
        return () -> {
            Flux<Scenario> scenariosFlux = scenarioSourceListener.getScenarios();
            scenariosFlux.subscribe(SCENARIO_QUEUE::add);
            getDelay();
            CDL.countDown();
        };
    }

    private Runnable getProxiesRunnable() {
        return () -> {
            Flux<ProxyConfigHolder> proxiesFlux = proxySourcesClient.getProxies();
            proxiesFlux.subscribe(PROXY_QUEUE::add);
            getDelay();
            CDL.countDown();
        };
    }

    private void getDelay() {
        try {
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Runnable getExecutionServiceRunnable() {
        return () -> {
            service.execute(SCENARIO_QUEUE, PROXY_QUEUE);
            CDL.countDown();
        };
    }

    private void await() {
        try {
            CDL.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Create ThreadPoolExecutor.
     *
     * @param threadPoolConfig the config for the ThreadPoolExecutor
     * @return the ThreadPoolExecutor entity
     */
    private ThreadPoolExecutor createThreadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        return new ThreadPoolExecutor(
                threadPoolConfig.getCorePoolSize(),
                defineMaximumAvailableProcessors(),
                threadPoolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Configure ThreadPoolConfig from properties file.
     *
     * @param propertiesConfig the properties from resources file
     * @param threadPoolConfig the ThreadPoolConfig entity
     */
    private void configureThreadPoolConfig(PropertiesConfig propertiesConfig, ThreadPoolConfig threadPoolConfig) {
        var properties = propertiesConfig.getProperties(THREAD_POOL_PROPERTIES);
        var corePoolSize = Integer.parseInt(properties.getProperty(CORE_POOL_SIZE));
        var keepAliveTime = Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME));
        threadPoolConfig.setCorePoolSize(corePoolSize);
        threadPoolConfig.setKeepAliveTime(keepAliveTime);
    }

    /**
     * Get the number of available processor cores.
     *
     * @return the number of available processor core
     */
    private int defineMaximumAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}
