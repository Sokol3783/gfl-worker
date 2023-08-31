package executor.service.infrastructure.impl;

import static executor.service.infrastructure.ClassScannerUtil.getClassScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface FactoryForDI<T> {

  static FactoryForDI runFactory()
      throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    //TODO SETTING PATH FROM properties
    Set<Class<? extends FactoryForDI>> subTypesOf = getClassScanner(null).getSubTypesOf(
        FactoryForDI.class);

    if (subTypesOf.isEmpty()) {
      if (Factory.factory == null) {
        return AbstractFactoryDI.getDefaultFactoryDI();
      }
      return Factory.factory;
    }

    //TODO make mark or rule for mark the necessary class
    List<Class> classes = new ArrayList<>();
    for (Class clazz : subTypesOf) {

      return (FactoryForDI) clazz.getDeclaredConstructor().newInstance();

    }

    if (classes.size() > 1 || classes.isEmpty()) {
      throw new InstantiationException("Несколько указателей фабрик!!");
    }
    return (FactoryForDI) classes.get(0).getDeclaredConstructor().newInstance();
  }

  T getObject(Class<T> clazz);

  final class Factory {

    private static FactoryForDI factory;

    static {
      try {
        factory = runFactory();
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }

    public Factory() throws InstantiationException, IllegalAccessException {
    }
  }


}
