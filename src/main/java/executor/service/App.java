package executor.service;

import executor.service.objectfactory.ObjectFactory;
import executor.service.objectfactory.ObjectFactoryImpl;
import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.scenarios.ScenarioSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {

    ObjectFactory factory = new ObjectFactoryImpl();
    ParallelFlowExecutorService mainService = factory.create(ParallelFlowExecutorService.class);
    mainService.execute(() -> log.info("Worker successfully run!"));

  }

}
