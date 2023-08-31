package executor.service.config.di;

import static executor.service.config.di.ClassScannerUtil.getClassScanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FactoryForDI<T> {

  static FactoryForDI runFactory() throws InstantiationException, IllegalAccessException {
    //TODO SETTING PATH FROM properties
    Set<Class<? extends FactoryForDI>> subTypesOf = getClassScanner(null).getSubTypesOf(
        FactoryForDI.class);

    if (subTypesOf.isEmpty()) {
      return getDefaultFactoryDI();
    }

    //TODO make mark or rule for mark the necessary class
    List<Class> classes = new ArrayList<>();
    for (Class clazz : subTypesOf) {
      if (true) {
        classes.add(clazz);
      }
    }

    if (classes.size() > 1 || classes.isEmpty()) {
      throw new InstantiationException("Несколько указателей фабрик!!");
    }

    return (FactoryForDI) classes.get(0).newInstance();

  }

  //TODO

  private static FactoryForDI getDefaultFactoryDI() {
    return new FactoryForDI() {

      final Map<Class, Object> map = new HashMap<>();

      @Override
      public Object getObject(Class clazz) {
        return map.merge(clazz, getClazzObject(clazz), (oldClazz, newClazz) -> oldClazz);
      }

      private Object getClazzObject(Class clazz) {
        return null;
      }

    };
  }

  T getObject(Class<T> clazz);


}
