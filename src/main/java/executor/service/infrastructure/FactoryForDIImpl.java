package executor.service.infrastructure;

import executor.service.infrastructure.context.ApplicationContextImp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryForDIImpl<T> implements FactoryForDI<T> {

  private final static Map<Class, Object> registry = new ConcurrentHashMap<>();
  private final ApplicationContextImp context;

  public FactoryForDIImpl(ApplicationContextImp context) {
    this.context = context;
  }

  @Override
  public T getObject(Class<T> clazz) throws Exception {
    return (T) registry.merge(clazz, getClazzObject(clazz), (oldClazz, newClazz) -> oldClazz);
  }

  private Object getClazzObject(Class<T> clazz)
      throws Exception {

    return context.getObject(clazz);

  }

}
