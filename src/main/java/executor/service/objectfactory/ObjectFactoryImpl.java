package executor.service.objectfactory;

import executor.service.model.configs.ThreadPoolConfig;
import executor.service.model.configs.WebDriverConfig;
import executor.service.model.proxy.ProxyCredentials;
import executor.service.model.proxy.ProxyNetworkConfig;
import executor.service.model.scenario.Scenario;
import executor.service.model.scenario.Step;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.executionservice.ExecutionService;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.Task;
import executor.service.service.parallelflowexecutor.TaskKeeper;
import executor.service.service.parallelflowexecutor.impls.ParallelFlowExecutorServiceImpl;
import executor.service.service.parallelflowexecutor.impls.TaskKeeperImpl;
import executor.service.service.parallelflowexecutor.impls.publishers.ProxyPublisher;
import executor.service.service.parallelflowexecutor.impls.publishers.ScenarioPublisher;
import executor.service.service.parallelflowexecutor.impls.subscribers.ExecutionSubscriber;
import executor.service.service.stepexecution.*;
import executor.service.service.stepexecution.impl.StepExecutionClickCssImpl;
import executor.service.service.stepexecution.impl.StepExecutionClickXpathImpl;
import executor.service.service.stepexecution.impl.StepExecutionFabricimpl;
import executor.service.service.stepexecution.impl.StepExecutionSleepImpl;
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
        synchronized (ObjectFactoryImpl.class){
            return INSTANCE.create(clazz);
        }
    }

    private enum Singleton implements ObjectFactory {
        INSTANCE;

        private final Reflections scanner = ClassScannerUtil.getClassScanner("executor.service");
        private final Map<Class, Object> context = new ConcurrentHashMap<>();

        @Override
        public <T> T create(Class<T> clazz) {
            return (T) context.merge(clazz, createInstance(clazz), (oldKey, newKey) -> oldKey);
        }


        //TODO подумать над уровнями проверок и возможностями
        private <T> boolean isNotAutoconfigure(Class<T> clazz) {
            List<Class> list = List.of(ParallelFlowExecutorService.class, TaskKeeper.class, Task.class,
                    WebDriverConfig.class, Scenario.class, Step.class, ProxyCredentials.class,
                    ProxyNetworkConfig.class, ThreadPoolConfig.class,
                    StepExecutionFabric.class, StepExecutionFabricimpl.class, StepExecutionClickCss.class,StepExecutionClickXpath.class, StepExecutionSleep.class,
                    ExecutionSubscriber.class);
            return list.stream().anyMatch(s -> s.equals(clazz));

        }

        private <T> T createNotAutoConfigureClass(Class<T> clazz) throws InstantiationException, NoSuchFieldException, IllegalAccessException {
            if (clazz.isAssignableFrom(ParallelFlowExecutorService.class)) {
                return createParallelFlowExecutorService();
            } else if (clazz.isAssignableFrom(TaskKeeper.class)) {
                return createTaskKeeper();
            } else if (clazz.isAssignableFrom(ThreadPoolConfig.class)) {
                return createThreadPoolConfig();
            } else if (clazz.isAssignableFrom(ExecutionSubscriber.class)) {
                return createExecutionSubscriber();
            } else if (clazz.isAssignableFrom(WebDriverConfig.class)) {
                return createWebDriverConfig();
            } else if (clazz.isAssignableFrom(StepExecutionFabric.class)) {
                return createStepExecutionFabrice();
            }
            throw new InstantiationException("Prohibition on creating a new instance  " + clazz.getName());
        }

        private <T> T createStepExecutionFabrice() {
            List<StepExecution> stepExecutions = new ArrayList<>();
            stepExecutions.add(new StepExecutionClickCssImpl());
            stepExecutions.add(new StepExecutionClickXpathImpl());
            stepExecutions.add(new StepExecutionSleepImpl());
            return (T) new StepExecutionFabricimpl(stepExecutions);
        }

        private <T> T createWebDriverConfig() {
            return (T) PropertyCreator.createWebDriverConfig();
        }

        private <T> T createThreadPoolConfig() {
            return (T) PropertyCreator.getThreadPoolConfig();
        }

        //TODO Если будет решена проблема костыля удалить
        private <T> T createExecutionSubscriber() {
            return (T) new ExecutionSubscriber(create(ProxyQueue.class)
                    ,create(ScenarioQueue.class)
                    ,create(ExecutionService.class),
                    null);
        }

        private <T> T createTaskKeeper() {
            List<TaskKeeper.TaskNode> nodes = new ArrayList<>();
            TaskKeeper.TaskNode publisherTask = new TaskKeeper.TaskNode(create(ProxyPublisher.class));
            nodes.add(publisherTask);
            TaskKeeper.TaskNode scenarioTask = new TaskKeeper.TaskNode(create(ScenarioPublisher.class));
            nodes.add(scenarioTask);
            TaskKeeper.TaskNode executor = new TaskKeeper.TaskNode(create(ExecutionSubscriber.class));
            nodes.add(executor);
            return (T) new TaskKeeperImpl(nodes);
        }

        private <T> T createParallelFlowExecutorService() throws NoSuchFieldException, IllegalAccessException {
            ThreadPoolConfig config = create(ThreadPoolConfig.class);
            ParallelFlowExecutorServiceImpl parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(config.getCorePoolSize()
                    , config.getCorePoolSize(),
                    config.getKeepAliveTime(),
                    config.getTimeUnit(),
                    Executors.defaultThreadFactory(),
                    new LinkedBlockingDeque<>(),
                    create(TaskKeeper.class));
            injectParallelFlowInCreateExecutionSubscriber(parallelFlowExecutorService);
            return (T) parallelFlowExecutorService;
        }

        //TODO КОСТЫЛЬ
        private void injectParallelFlowInCreateExecutionSubscriber(ParallelFlowExecutorServiceImpl parallelFlowExecutorService) throws NoSuchFieldException, IllegalAccessException {
            ExecutionSubscriber subscriber = create(ExecutionSubscriber.class);

            Field field = subscriber.getClass().getDeclaredField("parallelFlow");
            field.setAccessible(true);
            field.set(subscriber, parallelFlowExecutorService);
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
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        private Optional<Constructor<?>> findSuitableConstructor(Class<?> clazz) throws NoSuchMethodException, InstantiationException {
            Optional<Constructor<?>> first = Arrays.stream(clazz.getDeclaredConstructors()).filter(s -> s.getParameterCount() > 0).max(Comparator.comparing(Constructor::getParameterCount));
            if (first.isEmpty()) {
                return findEmptyConstructor(clazz);
            }
            return first;

        }

        private <T> Optional<Constructor<?>> findEmptyConstructor(Class<T> clazz){

            return Arrays.stream(clazz.getDeclaredConstructors()).findFirst();

        }

        private <T> T createInstanceWithConstructor(Constructor<T> constructor) throws IllegalAccessException,
                InvocationTargetException, InstantiationException {
            if (constructor.getParameterCount() > 0) {
                Object[] params = new Object[constructor.getParameterCount()];
                for (int i = 0; i < params.length; i++) {
                    Class<?> paramType = constructor.getParameterTypes()[i];
                    params[i] = create(paramType);
                }
                return constructor.newInstance(params);
            } else {
                return constructor.newInstance();
            }
        }
    }
}


