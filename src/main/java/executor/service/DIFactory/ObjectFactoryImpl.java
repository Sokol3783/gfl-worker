package executor.service.DIFactory;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectFactoryImpl implements ObjectFactory {
    private static final Singleton INSTANCE = Singleton.INSTANCE;

    private volatile static ObjectFactoryImpl instance = null;
    private ObjectFactoryImpl() {}
    public static ObjectFactoryImpl getInstance() {
        if (instance == null) {
            synchronized(ObjectFactoryImpl.class) {
                if (instance == null) {
                    instance = new ObjectFactoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public <T> T create(Class<T> clazz) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return INSTANCE.create(clazz);
    }

    private enum Singleton implements ObjectFactory {
        INSTANCE;
        private static final Map<Class,Object> context = new ConcurrentHashMap<>();

        private final Reflections scanner = ClassScannerUtil.getClassScanner("executor.service");

        @Override
        public <T> T create(Class<T> clazz) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Object object = context.get(clazz);
            if (object == null) {
                if (clazz.isInterface()) {
                    Set<Class<? extends T>> subTypesOf = scanner.getSubTypesOf(clazz);
                    if (subTypesOf.iterator().hasNext()) {
                        object = createInstance(subTypesOf.iterator().next());
                        context.put(clazz, object);
                    } else {
                        throw new InstantiationException("No implementation of class");
                    }
                } else {
                    object = createInstance(clazz);
                    context.put(clazz, object);
                }
            }
            return (T) object;
        }

        private Object createInstance(Class clazz)
                throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            Constructor declaredConstructor = clazz.getDeclaredConstructor();
            Object o = declaredConstructor.newInstance();
            if (declaredConstructor.getParameterCount() > 0) {
                return o;
            }

            for (Type type : declaredConstructor.getParameterTypes()) {
                Field[] declaredFields = o.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    if (type.equals(field.getType())) {
                        field.setAccessible(true);
                        field.set(field, create(type.getClass()));
                    }
                }
            }
            return o;
        }
    }

}
