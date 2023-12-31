package executor.service.objectfactory;

import executor.service.App;
import executor.service.model.configs.ThreadPoolConfig;
import executor.service.model.configs.WebDriverConfig;
import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.proxy.ProxyCredentials;
import executor.service.model.proxy.ProxyNetworkConfig;
import executor.service.model.scenario.Scenario;
import executor.service.model.scenario.Step;
import executor.service.model.service.ContinuousOperationNode;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Operatable;
import executor.service.service.parallelflowexecutor.ContinuousOperations;
import executor.service.service.parallelflowexecutor.impls.ParallelFlowExecutorServiceImpl;
import executor.service.service.parallelflowexecutor.impls.ContinuousOperationsImpl;
import executor.service.service.parallelflowexecutor.impls.publishers.ProxyPublisher;
import executor.service.service.parallelflowexecutor.impls.publishers.ScenarioPublisher;
import executor.service.service.parallelflowexecutor.impls.subscribers.ExecutableScenarioComposerImpl;
import executor.service.service.parallelflowexecutor.impls.subscribers.ExecutionSubscriber;
import executor.service.service.parallelflowexecutor.impls.subscribers.ExecutableScenarioComposer;
import executor.service.service.stepexecution.*;
import executor.service.service.stepexecution.impl.*;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;

public class ObjectFactoryImpl implements ObjectFactory {

    private static final Singleton INSTANCE = Singleton.INSTANCE;

    @Override
    public <T> T create(Class<T> clazz) {
        return INSTANCE.create(clazz);
    }

    private enum Singleton implements ObjectFactory {
        INSTANCE;

        private final Reflections scanner = ClassScannerUtil.getClassScanner(App.class.getPackageName());
        private final Map<Class, Object> context = new ConcurrentHashMap<>();

        @Override
        public <T> T create(Class<T> clazz) {
            return (T) context.merge(clazz, createInstance(clazz), (oldKey, newKey) -> oldKey);
        }


        private <T> boolean isNotAutoconfigure(Class<T> clazz) {
            List<Class> list = List.of(ParallelFlowExecutorService.class, ContinuousOperations.class,
                    Operatable.class, ExecutionSubscriber.class,
                    WebDriverConfig.class, Scenario.class, Step.class, ProxyCredentials.class,
                    ProxyNetworkConfig.class, ThreadPoolConfig.class,
                    StepExecutionFabric.class, StepExecutionFabricImpl.class,
                    StepExecutionClickCss.class,StepExecutionClickXpath.class, StepExecutionSleep.class);
            return list.stream().anyMatch(s -> s.equals(clazz));

        }

        private <T> T createNotAutoConfigureClass(Class<T> clazz) throws InstantiationException, NoSuchFieldException, IllegalAccessException {
            if (clazz.isAssignableFrom(ParallelFlowExecutorService.class)) {
                return createParallelFlowExecutorService();
            } else if (clazz.isAssignableFrom(ContinuousOperations.class)) {
                return createTaskKeeper();
            } else if (clazz.isAssignableFrom(ThreadPoolConfig.class)) {
                return (T) createThreadPoolConfig();
            } else if (clazz.isAssignableFrom(ExecutionSubscriber.class)) {
                return (T) createExecutionSubscriber();
            } else if (clazz.isAssignableFrom(WebDriverConfig.class)) {
                return (T) createWebDriverConfig();
            } else if (clazz.isAssignableFrom(StepExecutionFabric.class)) {
                return createStepExecutionFabrice();
            } else if (clazz.isAssignableFrom(ExecutableScenarioComposerImpl.class)) {
                return createExecutableScenarioComposerImpl();
            } else if (clazz.isAssignableFrom(ProxyNetworkConfig.class)) {
                return (T) getDefaultProxy();
            }
            throw new InstantiationException("Prohibition on creating a new instance  " + clazz.getName());
        }

        private <T> T createExecutableScenarioComposerImpl() {
            return (T) new ExecutableScenarioComposerImpl(create(ProxyQueue.class),
                    create(ScenarioQueue.class),
                    getDefaultProxy());
        }

        private ProxyConfigHolder getDefaultProxy() {
            return new ProxyConfigHolder();
        }

        private <T> T createStepExecutionFabrice() {
            List<StepExecution> stepExecutions = new ArrayList<>();
            stepExecutions.add(new StepExecutionClickCssImpl());
            stepExecutions.add(new StepExecutionClickXpathImpl());
            stepExecutions.add(new StepExecutionSleepImpl());
            return (T) new StepExecutionFabricImpl(stepExecutions);
        }

        private <T extends WebDriverConfig> T createWebDriverConfig() throws InstantiationException {
            return (T) PropertyCreator.createWebDriverConfig();
        }

        private <T extends ThreadPoolConfig> T createThreadPoolConfig() throws InstantiationException {
            return (T) PropertyCreator.getThreadPoolConfig();
        }

        private <T extends ExecutionSubscriber> T createExecutionSubscriber() {
            return (T) new ExecutionSubscriber(
                    create(ExecutionService.class),
                    null,
                    create(ExecutableScenarioComposer.class));
        }

        private <T extends ContinuousOperations> T createTaskKeeper() {
            List<ContinuousOperationNode> nodes = new ArrayList<>();
            ContinuousOperationNode publisherTask = new ContinuousOperationNode(create(ProxyPublisher.class));
            nodes.add(publisherTask);
            ContinuousOperationNode scenarioTask = new ContinuousOperationNode(create(ScenarioPublisher.class));
            nodes.add(scenarioTask);
            ContinuousOperationNode executor = new ContinuousOperationNode(create(ExecutionSubscriber.class));
            nodes.add(executor);
            return (T) new ContinuousOperationsImpl(nodes);
        }

        private <T extends ParallelFlowExecutorService> T createParallelFlowExecutorService() throws NoSuchFieldException, IllegalAccessException {
            ThreadPoolConfig config = create(ThreadPoolConfig.class);
            ParallelFlowExecutorServiceImpl parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(config.getCorePoolSize()
                    , config.getCorePoolSize(),
                    config.getKeepAliveTime(),
                    config.getTimeUnit(),
                    (ThreadFactory) context.put(ThreadFactory.class, Executors.defaultThreadFactory()),
                    new LinkedBlockingDeque<>(),
                    create(ContinuousOperations.class));
            injectParallelFlowInCreateExecutionSubscriber(parallelFlowExecutorService);
            return (T) parallelFlowExecutorService;
        }

        private void injectParallelFlowInCreateExecutionSubscriber(ParallelFlowExecutorServiceImpl parallelFlowExecutorService) throws NoSuchFieldException, IllegalAccessException {
            ExecutionSubscriber subscriber = create(ExecutionSubscriber.class);
            Field field = subscriber.getClass().getDeclaredField("parallelFlow");
            field.setAccessible(true);
            field.set(subscriber, parallelFlowExecutorService);
            field.setAccessible(false);
        }

        private <T> T createInstance(Class<T> clazz) {
            try {
                if (isNotAutoconfigure(clazz)) {
                    return createNotAutoConfigureClass(clazz);
                } else {
                    if (clazz.isInterface()) {
                        Set<Class<? extends T>> subTypesOf = scanner.getSubTypesOf(clazz);
                        if (!subTypesOf.isEmpty()) {
                            clazz = (Class<T>) subTypesOf.iterator().next();
                            return createInstance(clazz);
                        }
                    } else {
                        Optional<Constructor<?>> suitableConstructor = findSuitableConstructor(clazz);
                        if (suitableConstructor.isPresent()) {
                            return createInstanceWithConstructor((Constructor<T>) suitableConstructor.get());
                        }
                    }
                }
                throw new InstantiationException("No suitable constructor found for class: " + clazz.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        private Optional<Constructor<?>> findSuitableConstructor(Class<?> clazz) {
            Optional<Constructor<?>> maxConstructor = findMaxConstructor(clazz);
            if (maxConstructor.isEmpty()) {
                return findEmptyConstructor(clazz);
            }
            return maxConstructor;
        }

        private Optional<Constructor<?>> findMaxConstructor(Class<?> clazz) {
            return Arrays.stream(clazz.getDeclaredConstructors())
                    .max(Comparator.comparing(Constructor::getParameterCount));
        }

        private <T> Optional<Constructor<?>> findEmptyConstructor(Class<T> clazz){
            return Arrays.stream(clazz.getDeclaredConstructors()).findFirst();
        }

        private <T> T createInstanceWithConstructor(Constructor<T> constructor) throws IllegalAccessException,
                InvocationTargetException, InstantiationException {
            if (constructor.getParameterCount() > 0) {
                return constructor.newInstance(fillClassesInConstructor(constructor));
            }
            return constructor.newInstance();
        }

        private Object fillClassesInConstructor(Constructor constructor) {
            Object[] params = new Object[constructor.getParameterCount()];
            for (int i = 0; i < params.length; i++) {
                Class<?> paramType = constructor.getParameterTypes()[i];
                params[i] = create(paramType);
            }
            return params;
        }
    }
}


