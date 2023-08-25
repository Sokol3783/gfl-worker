package executor.service.service.di;

import executor.service.config.properties.PropertiesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    private static final Logger log = LoggerFactory.getLogger(PropertiesConfig.class);

    private final ApplicationContext context;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(getAClass(aClass));
        }
    }

    public <T> T createObject(Class<T> implClass) {
        T t = create(implClass);

        configure(t);

        return t;
    }

    private ObjectConfigurator getAClass(Class<? extends ObjectConfigurator> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            log.info("Error creating ObjectConfigurator instance for class: " + aClass.getName());
            throw new RuntimeException(e);
        }
    }

    private <T> T create(Class<T> implClass) {
        try {
            return implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            log.info("Error creating instance of class: " + implClass.getName());
            throw new RuntimeException(e);
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
    }
}
