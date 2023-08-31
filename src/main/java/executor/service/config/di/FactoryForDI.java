package executor.service.config.di;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

public interface FactoryForDI<T> {

  Reflections scanner = new Reflections("/src/java/executor/service/");

   T getObject(Class<T> clazz);

  static FactoryForDI runFactory() throws InstantiationException, IllegalAccessException {

    Set<Class<? extends FactoryForDI>> subTypesOf = scanner.getSubTypesOf(FactoryForDI.class);

    if (subTypesOf.isEmpty()) {
      return getDefaultFactoryDI();
    }

    List<Class> classes =new ArrayList<>();

    for (Class clazz : subTypesOf) {
      if (true) {
        classes.add(clazz);
      }
    };

    if (classes.size() > 1 || classes.isEmpty()) {
      throw new InstantiationException("Несколько указателей фабрик!!");
    };

    return (FactoryForDI) classes.get(0).newInstance();

  }

  static FactoryForDI getDefaultFactoryDI() {
    return new FactoryForDI() {
      @Override
      public Object getObject(Class clazz) {
        return null;
      }
    };
  }

}
