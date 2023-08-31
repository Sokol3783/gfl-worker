package executor.service.config.di;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectFactory {

  private static final Logger log = LoggerFactory.getLogger(ObjectFactory.class);

  private final ApplicationContext context;
  private final List<ObjectConfigurator> configurators;

  public ObjectFactory(ApplicationContext context, List<ObjectConfigurator> configurators) {
    this.context = context;
    this.configurators = configurators;
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
    configurators.forEach(objectConfigurator -> objectConfigurator.configure(t));
  }
}
