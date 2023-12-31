package executor.service.objectfactory;

import executor.service.App;
import org.reflections.Reflections;

class ClassScannerUtil {

  private ClassScannerUtil() {
  }

  private static Reflections CLASS_SCANNER = new Reflections(App.class.getPackageName());

  public static Reflections getClassScanner(String path) {
    if (path != null && !path.isEmpty()) {
      CLASS_SCANNER = new Reflections(path);
    }
    return CLASS_SCANNER;
  }

}
