package executor.service.service.di;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

    public <T> T createObject(Class<T> implClass) {
        T t;
        try {
            t = create(implClass);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException,
            java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}
