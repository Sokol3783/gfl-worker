package executor.service.infrastructure.context;

import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class JavaConfig implements Config {

  private final Reflections scanner;
  private final Map<Class, Class> ifc2ImplClass;

  public JavaConfig(Reflections scanner, Map<Class, Class> ifc2ImplClass) {
    this.ifc2ImplClass = ifc2ImplClass;
    this.scanner = scanner;
  }


  @Override
  public <T> Class<? extends T> getClass(Class<T> ifc) throws Exception {
    return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
      Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
      if (classes.size() != 1) {
        throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
      }
      return classes.iterator().next();
    });
  }
}
