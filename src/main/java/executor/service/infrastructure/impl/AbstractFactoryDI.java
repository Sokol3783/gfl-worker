package executor.service.infrastructure.impl;

import executor.service.infrastructure.ClassScannerUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

class AbstractFactoryDI {

  private final static Object sync = new Object();
  private final static Reflections scanner = ClassScannerUtil.getClassScanner("");

  private static Object getDefaultClazzObject(Class clazz)
      throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    synchronized (sync) {
      if (clazz.isInterface()) {
        Set<Class> subTypesOf = scanner.getSubTypesOf(clazz);
        if (subTypesOf.iterator().hasNext()) {
          return createInstance(subTypesOf.iterator().next());
        }
        throw new InstantiationException("No implementation of class");
      }
      return createInstance(clazz);
    }
  }

  private static Object createInstance(Class clazz)
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
          field.set(field, getDefaultClazzObject(type.getClass()));
        }
      }
    }
    return o;
  }

  static FactoryForDI getDefaultFactoryDI() {

    FactoryForDI factoryForDI = new FactoryForDI() {

      final Map<Class, Object> map = new HashMap<>();

      @Override
      public Object getObject(Class clazz) {
        return map.merge(clazz, getClazzObject(clazz), (oldClazz, newClazz) -> oldClazz);
      }

      private Object getClazzObject(Class clazz) {
        try {
          return getDefaultClazzObject(clazz);
        } catch (Throwable e) {
          throw new RuntimeException(e.getMessage(), e);
        }
      }
    };
    return factoryForDI;
  }

}
