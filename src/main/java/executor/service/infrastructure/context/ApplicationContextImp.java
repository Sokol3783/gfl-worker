package executor.service.infrastructure.context;

import executor.service.infrastructure.ClassScannerUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.reflections.Reflections;

public class ApplicationContextImp implements ApplicationContext {

  private static Reflections scanner = ClassScannerUtil.getClassScanner("");
  private final Map<Class, List<Config>> configs;

  public ApplicationContextImp() {
    configs = new HashMap<>();
  }

  public ApplicationContextImp(Config config) {
    this.configs = new HashMap<>();
  }

  public <T> T getObject(Class<T> type)
      throws Exception {

    List<Config> registry = configs.get(type);

    if (registry.isEmpty()) {
      return type.getDeclaredConstructor().newInstance();
    }

    return (T) registry.get(0).getClass(type);


  }
}
