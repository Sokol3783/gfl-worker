package executor.service.DIFactory;

import java.lang.reflect.InvocationTargetException;

public interface ObjectFactory {
    <T> T create(Class<T> clazz) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
