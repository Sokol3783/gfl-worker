package executor.service;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.scenario.Scenario;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.TaskKeeper;
import executor.service.service.parallelflowexecutor.impls.ParallelFlowExecutorServiceImpl;
import executor.service.service.parallelflowexecutor.impls.TaskKeeperImpl;
import executor.service.service.parallelflowexecutor.impls.publishers.ProxyPublisher;
import executor.service.service.parallelflowexecutor.impls.publishers.ScenarioPublisher;
import executor.service.service.parallelflowexecutor.impls.subscribers.ExecutionSubscriber;
import executor.service.service.proxy.impl.JSONFileProxyProvider;
import executor.service.service.proxy.impl.ProxySourcesClientImpl;
import executor.service.service.proxy.impl.ProxyValidatorImpl;
import executor.service.service.scenarios.impl.JSONFileScenarioProvider;
import executor.service.service.scenarios.impl.ScenarioSourceListenerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) {

        ProxyQueue proxyQueue = new ProxyQueue();
        ScenarioQueue scenarioQueue = new ScenarioQueue();
        ThreadFactory factory = Executors.defaultThreadFactory();
        ProxyPublisher proxyPublisher = new ProxyPublisher(proxyQueue, new ProxySourcesClientImpl(new JSONFileProxyProvider(), new ProxyValidatorImpl()));
        ScenarioPublisher scenarioPublisher = new ScenarioPublisher(scenarioQueue, new ScenarioSourceListenerImpl(new JSONFileScenarioProvider()));
        ParallelFlowExecutorService service = null;
        ExecutionService executionService = new ExecutionService() {
            @Override
            public void execute(Scenario scenario, ProxyConfigHolder proxy) {
                System.out.println(scenario.toString());
                System.out.println("Tra-lala-la\n");
            }
        };
        BlockingQueue queue = new ArrayBlockingQueue(300, true);

        List<TaskKeeper.TaskNode> taskNodes = new ArrayList<>();
        taskNodes.add(new TaskKeeper.TaskNode(proxyPublisher));
        taskNodes.add(new TaskKeeper.TaskNode(scenarioPublisher));
        TaskKeeper taskKeeper = new TaskKeeperImpl(taskNodes);

        service = setService(service, factory, queue, taskKeeper);
        ExecutionSubscriber subscriber = new ExecutionSubscriber(proxyQueue,
                scenarioQueue, executionService, service);
        taskNodes.add(new TaskKeeper.TaskNode(subscriber));
        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Wooohooo!");
            }
        });
    }

    private static ParallelFlowExecutorService setService(ParallelFlowExecutorService service, ThreadFactory factory, BlockingQueue queue, TaskKeeper taskKeeper) {

        service = new ParallelFlowExecutorServiceImpl(6,
                24, 100, TimeUnit.SECONDS, factory, queue, taskKeeper
        );
        return service;
    }
}
