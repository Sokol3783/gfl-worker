package executor.service.config.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {

  ApplicationContext context;

  public InjectByTypeAnnotationObjectConfigurator(ApplicationContext context) {
    this.context = context;
  }

  @Override
  public void configure(Object t) {
    for (Field field : t.getClass().getDeclaredFields()) {
      if (isFieldNotPrimitiveOrWrapper(field) &&
          isFieldInjectable(t, field)) {
        field.setAccessible(true);
        Object object = context.getObject(field.getType());
        try {
          field.set(t, object);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private boolean isFieldNotPrimitiveOrWrapper(Field field) {
    Class<?> fieldType = field.getType();
    List<Class<?>> excludedClasses = Arrays.asList(
        String.class, Boolean.class, Character.class,
        Byte.class, Short.class, Integer.class,
        Long.class, Float.class, Double.class, Void.class
    );
    return !fieldType.isPrimitive() && !excludedClasses.contains(fieldType);
  }

  private boolean isFieldInjectable(Object t, Field field) {
    try {
      Constructor<?>[] constructors = t.getClass().getDeclaredConstructors();

      for (Constructor<?> constructor : constructors) {
        Parameter[] parameters = constructor.getParameters();
        for (Parameter parameter : parameters) {
          Class<?> parameterType = parameter.getType();
          if (parameterType.isAssignableFrom(field.getType())) {
            return true;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
