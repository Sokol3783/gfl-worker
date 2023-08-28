package executor.service.config.di;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private ObjectFactory factory;
    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();
    private final Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    public Config getConfig() {
        return config;
    }

    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return type.cast(cache.get(type));
        }

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = factory.createObject(implClass);

        cache.put(type, t);

        return t;
    }

    public void setObjectToCache(Object o) {
        cache.put(o.getClass(), o);
    }
}
