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

        Future<Flux<Scenario>> scenariosFuture = threadPoolExecutor.submit(scenarioSourceListener::getScenarios);
        Flux<Scenario> scenariosFlux= getScenariosFlux(scenariosFuture);
        scenariosFlux.subscribe(SCENARIO_QUEUE::add);
        CDL.countDown();

        Future<Flux<ProxyConfigHolder>> proxiesFuture = threadPoolExecutor.submit(proxySourcesClient::getProxies);
        Flux<ProxyConfigHolder> proxiesFlux= getProxiesFlux(proxiesFuture);
        proxiesFlux.subscribe(PROXY_QUEUE::add);
        CDL.countDown();

        threadPoolExecutor.execute(() -> service.execute(SCENARIO_QUEUE, PROXY_QUEUE));
        CDL.countDown();

        await();
        threadPoolExecutor.shutdown();
    }

    private Flux<Scenario> getScenariosFlux(Future<Flux<Scenario>> scenariosFuture) {
        Flux<Scenario> scenariosFlux;
        try {
            scenariosFlux = scenariosFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return scenariosFlux;
    }

    private Flux<ProxyConfigHolder> getProxiesFlux(Future<Flux<ProxyConfigHolder>> proxiesFuture) {
        Flux<ProxyConfigHolder> proxiesFlux;
        try {
            proxiesFlux = proxiesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return proxiesFlux;
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
