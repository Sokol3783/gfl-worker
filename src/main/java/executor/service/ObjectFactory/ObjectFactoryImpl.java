package executor.service.ObjectFactory;

import executor.service.config.properties.PropertiesConfig;
import executor.service.model.configs.ThreadPoolConfig;
import executor.service.model.configs.WebDriverConfig;
import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.model.proxy.ProxyCredentials;
import executor.service.model.proxy.ProxyNetworkConfig;
import executor.service.model.scenario.Scenario;
import executor.service.model.scenario.Step;
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
import executor.service.service.stepexecution.StepExecutionClickCss;
import executor.service.service.stepexecution.StepExecutionClickXpath;
import executor.service.service.stepexecution.StepExecutionFabric;
import executor.service.service.stepexecution.StepExecutionSleep;
import executor.service.service.webdriver.WebDriverInitializer;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static executor.service.config.properties.PropertiesConstants.*;

public class ObjectFactoryImpl implements ObjectFactory {

    private static final Singleton INSTANCE = Singleton.INSTANCE;

    private static final String PATH_TO_THREAD_PROPERTIES = "thread-pool.properties";



    @Override
    public <T> T create(Class<T> clazz) {
        return INSTANCE.create(clazz);
    }

    private enum Singleton implements ObjectFactory {
        INSTANCE;

        private final Reflections scanner = ClassScannerUtil.getClassScanner("executor.service");
        private final Map<Class, Object> context = new ConcurrentHashMap<>();

        @Override
        public <T> T create(Class<T> clazz) {
            Object object = context.computeIfAbsent(clazz, key -> createInstance(clazz));
            return clazz.cast(object);
        }

        private <T> boolean isNotAutoconfigure(Class<T> clazz) {
            List<Class> list = List.of(ParallelFlowExecutorService.class, WebDriverConfig.class,
                    TaskKeeper.class, Scenario.class, Step.class, ProxyConfigHolder.class, ProxyCredentials.class,
                    ProxyNetworkConfig.class, ThreadPoolConfig.class, WebDriverInitializer.class, StepExecutionFabric.class
                    , StepExecutionClickCss.class, StepExecutionClickXpath.class, StepExecutionFabric.class, StepExecutionSleep.class,
                    ExecutionSubscriber.class);
            return list.stream().anyMatch(s -> s.equals(clazz));
        }

        private <T> T createNotAutoConfigureClass(Class<T> clazz) throws InstantiationException {
            if (clazz.isAssignableFrom(ParallelFlowExecutorService.class)) {
                return createParallelFlowExecutorService();
            } else if (clazz.isAssignableFrom(TaskKeeper.class)) {
                return createTaskKeeper();
            } else if (clazz.isAssignableFrom(ThreadPoolConfig.class)) {
                return createThreadPoolConfig();
            } else if (clazz.isAssignableFrom(ExecutionSubscriber.class)) {
                return createExecutionSubscriber();
            }

            throw new InstantiationException("Not supported instantiation  for " + clazz.getName());
        }

        private <T> T createExecutionSubscriber() {
            return (T) new ExecutionSubscriber(create(ProxyQueue.class)
                    ,create(ScenarioQueue.class)
                    ,create(ExecutionService.class),
                    null);
        }

        private <T> T createThreadPoolConfig() {

            ThreadPoolConfig pool = new ThreadPoolConfig();
            try {
                Properties properties = PropertiesConfig.getProperties(PATH_TO_THREAD_PROPERTIES);
                pool.setCorePoolSize(Integer.parseInt(properties.getProperty(CORE_POOL_SIZE, "2")));
                pool.setKeepAliveTime(Long.parseLong(properties.getProperty(KEEP_ALIVE_TIME, "100")));
                pool.setTimeUnit(TimeUnit.valueOf(properties.getProperty(TIMEUNIT,"SECONDS")));
            } catch (Exception e) {
                pool =   new ThreadPoolConfig(2, 100L, TimeUnit.SECONDS);
            }

            return (T) pool;
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

        private <T> T createParallelFlowExecutorService() {
            TaskKeeper taskKeeper = create(TaskKeeper.class);
            ThreadPoolConfig config = create(ThreadPoolConfig.class);
            ParallelFlowExecutorServiceImpl parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(config.getCorePoolSize()
                    , config.getCorePoolSize(),
                    config.getKeepAliveTime(),
                    config.getTimeUnit(),
                    Executors.defaultThreadFactory(),
                    new LinkedBlockingDeque<>(),
                    taskKeeper);
            injectParallelFlowInCreateExecutionSubscriber(parallelFlowExecutorService);

            return (T) parallelFlowExecutorService;
        }

        private void injectParallelFlowInCreateExecutionSubscriber(ParallelFlowExecutorServiceImpl parallelFlowExecutorService) {

        }

        private synchronized <T> T createInstance(Class<T> clazz) {
            try {
                if (isNotAutoconfigure(clazz)) {
                    return createNotAutoConfigureClass(clazz);
                } else {
                    Constructor<T> constructor = findSuitableConstructor(clazz);
                    if (constructor != null) {
                        return createInstanceWithConstructor(constructor);
                    } else if (clazz.isInterface()) {
                        Set<Class<? extends T>> subTypesOf = scanner.getSubTypesOf(clazz);
                        if (!subTypesOf.isEmpty()) {
                            clazz = (Class<T>) subTypesOf.iterator().next();
                            return createInstance(clazz);
                        }
                    }
                }
                throw new InstantiationException("No suitable constructor found for class: " + clazz.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        private <T> Constructor<T> findSuitableConstructor(Class<T> clazz) throws NoSuchMethodException {
            Constructor<T> constructor = null;
            try {
                constructor = clazz.getDeclaredConstructor();
            } catch (NoSuchMethodException ignored) {
                Constructor<?>[] constructors = clazz.getDeclaredConstructors();
                for (Constructor<?> c : constructors) {
                    if (c.getParameterCount() > 0) {
                        constructor = (Constructor<T>) c;
                        break;
                    }
                }
            }
            return constructor;
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
                System.out.println(constructor.getName());
                return constructor.newInstance();
            }
        }
    }
}


