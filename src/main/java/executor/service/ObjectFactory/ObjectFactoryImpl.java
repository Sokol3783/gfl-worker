package executor.service.ObjectFactory;

import executor.service.config.properties.PropertiesConstants;
import executor.service.queue.ProxyQueue;
import executor.service.queue.ScenarioQueue;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.TaskKeeper;
import executor.service.service.parallelflowexecutor.impls.ParallelFlowExecutorServiceImpl;
import executor.service.service.parallelflowexecutor.impls.TaskKeeperImpl;
import executor.service.service.parallelflowexecutor.impls.publishers.ProxyPublisher;
import executor.service.service.parallelflowexecutor.impls.publishers.ScenarioPublisher;
import executor.service.service.proxy.impl.JSONFileProxyProvider;
import executor.service.service.proxy.impl.ProxySourcesClientImpl;
import executor.service.service.proxy.impl.ProxyValidatorImpl;
import executor.service.service.scenarios.impl.JSONFileScenarioProvider;
import executor.service.service.scenarios.impl.ScenarioSourceListenerImpl;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
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

        private final Reflections scanner = ClassScannerUtil.getClassScanner("executor.service");
        private final Map<Class, Object> context = new ConcurrentHashMap<>();

        @Override
        public <T> T create(Class<T> clazz) {
            Object object = context.computeIfAbsent(clazz, key -> createInstance(clazz));
            return clazz.cast(object);
        }

        private <T> boolean isNotAutoconfigure(Class<T> clazz) {
            if (ParallelFlowExecutorService.class.isAssignableFrom(clazz)) {
                return true;
            }
            return false;
        }
        private <T> T createNotAutoConfigureClass(Class<T> clazz) {
            if (clazz.isAssignableFrom(ParallelFlowExecutorService.class)) {
                ProxyQueue proxyQueue = new ProxyQueue();
                ScenarioQueue scenarioQueue = new ScenarioQueue();
                ProxyPublisher proxyPublisher = new ProxyPublisher(proxyQueue,
                        new ProxySourcesClientImpl(new JSONFileProxyProvider(), new ProxyValidatorImpl()));
                ScenarioPublisher scenarioPublisher = new ScenarioPublisher(scenarioQueue,
                        new ScenarioSourceListenerImpl(new JSONFileScenarioProvider()));
                List<TaskKeeper.TaskNode> taskNodes = new ArrayList<>();
                taskNodes.add(new TaskKeeper.TaskNode(proxyPublisher));
                taskNodes.add(new TaskKeeper.TaskNode(scenarioPublisher));
                TaskKeeper taskKeeper = new TaskKeeperImpl(taskNodes);
                return (T) new ParallelFlowExecutorServiceImpl(6
                        ,24,
                        100,
                        TimeUnit.SECONDS,
                        Executors.defaultThreadFactory(),
                        new ArrayBlockingQueue(300, true),
                        taskKeeper);
            }
            return null;
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


