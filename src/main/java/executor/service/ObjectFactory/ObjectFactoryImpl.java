package executor.service.ObjectFactory;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
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

        private synchronized <T> T createInstance(Class<T> clazz) {
            try {
                Constructor<T> constructor = findSuitableConstructor(clazz);
                if (constructor != null) {
                    return createInstanceWithConstructor(constructor);
                } else if (clazz.isInterface()) {
                    Set<Class<? extends T>> subTypesOf = scanner.getSubTypesOf(clazz);
                    if (!subTypesOf.isEmpty()) {
                        clazz = (Class<T>) subTypesOf.iterator().next();
                        return createInstance(clazz);
                    } else {
                        // Получаеться сюда идут наши костыли,то есть классы которые не в пакете service
                        if (ThreadFactory.class.isAssignableFrom(clazz)) {
                            return (T) Executors.defaultThreadFactory();
                        } else if (BlockingQueue.class.isAssignableFrom(clazz)) {
                            return (T) new ArrayBlockingQueue(300, true);
                        } else if (List.class.isAssignableFrom(clazz)) {
                            return (T) new ArrayList<>();
                        } else {
                            throw new InstantiationException("No implementation found for interface: " + clazz.getName());
                        }
                    }
                } else {
                    throw new InstantiationException("No suitable constructor found for class: " + clazz.getName());
                }
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
                    if (paramType.isPrimitive() || paramType.isEnum()) {
                        if (paramType.isPrimitive()) {
                            /* Сюда что-то рандомное пока что поставил что бы инициализировалось,а вообще
                            Было бы неплохо что бы примитивы с пропертей инициализировались или с нужного
                            класса
                            */
                            try {
                                params[i] = 10000;
                            } catch (IllegalFormatConversionException e) {
                                params[i] = "str";
                            }
                        } else if (paramType.isEnum()) {
                            // Костыль для енамов
                            params[i] = TimeUnit.SECONDS;
                        }
                    } else {
                        params[i] = create(paramType);
                    }
                }
                return constructor.newInstance(params);
            } else {
                System.out.println(constructor.getName());
                return constructor.newInstance();
            }
        }
    }
}


