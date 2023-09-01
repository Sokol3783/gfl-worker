package executor.service;

import executor.service.infrastructure.FactoryForDI;
import executor.service.service.ParallelFlowExecutorService;

public class App {

  public static void main(String[] args)
      throws Throwable {
    FactoryForDI factoryForDI = FactoryForDI.runFactory();
    ParallelFlowExecutorService object = (ParallelFlowExecutorService) factoryForDI.getObject(
        ParallelFlowExecutorService.class);

    /*
        ApplicationContext context = FactoryForDIImpl.run(
                getPackageName(),
                new HashMap<>(Map.of(ParalleFlowExecutorService.class, ParalleFlowExecutorService.class)));
        ParalleFlowExecutorService service = context.getObject(ParalleFlowExecutorService.class);
        service.execute();
         */
  }

  private static String getPackageName() {
    Package currentPackage = App.class.getPackage();
    return currentPackage.getName();
  }
}
