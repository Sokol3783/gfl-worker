package executor.service.config.di;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryForDIImpl<T> implements FactoryForDI<T> {

  private final static Map<Class, Object> registry = new ConcurrentHashMap<>();
  private final ApplicationContext context;
  private final Config config;

  public FactoryForDIImpl(ApplicationContext context, Config config) {
    this.context = context;
    this.config = config;
  }

  /**
   * get implementation of class using looking for class or interface's implementation
   * <p>
   * TODO best way is looking for class which implement all INTERFACE,
   * but there are no sense do it, so it should catch first interface and don't work
   * also it would be good to work with CJLIB(?) and DynamicProxy
   */

  @Override
  public T getObject(Class<T> clazz) {
    return (T) registry.merge(clazz, getClazzObject(clazz), (oldClazz, newClazz) -> oldClazz);
  }


  //TODO looking for interface's implementation or class;


  private Object getClazzObject(Class<T> clazz) {
    if (clazz.isInterface()) {
      return null;
    }
    return null;
  }

  /* TODO
  public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass) {
    JavaConfig config = new JavaConfig(packageToScan, ifc2ImplClass);
    ApplicationContext context = new ApplicationContext(config);
    ObjectFactory objectFactory = new ObjectFactory(context);
    context.setFactory(objectFactory);
    return context;
  }
   */


}
