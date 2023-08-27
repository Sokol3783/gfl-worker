package executor.service.config.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object t, ApplicationContext context) {
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
        return !fieldType.isPrimitive()
                && fieldType != String.class
                && fieldType != Boolean.class
                && fieldType != Character.class
                && fieldType != Byte.class
                && fieldType != Short.class
                && fieldType != Integer.class
                && fieldType != Long.class
                && fieldType != Float.class
                && fieldType != Double.class
                && fieldType != Void.class;
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
