package executor.service;

import executor.service.ObjectFactory.ObjectFactory;
import executor.service.ObjectFactory.ObjectFactoryImpl;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;

public class App {

  public static void main(String[] args) {

    ObjectFactory factory = new ObjectFactoryImpl();
    ParallelFlowExecutorService mainService = factory.create(ParallelFlowExecutorService.class);
    mainService.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("Run, Fedya, run");
      }
    });
  }

}
