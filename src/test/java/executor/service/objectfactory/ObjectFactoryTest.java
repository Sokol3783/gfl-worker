package executor.service.objectfactory;

import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.parallelflowexecutor.impls.ParallelFlowExecutorServiceImpl;
import executor.service.service.parallelflowexecutor.impls.subscribers.ExecutableScenarioComposerImpl;
import executor.service.service.parallelflowexecutor.impls.subscribers.ExecutableScenarioComposer;
import executor.service.service.scenarios.ScenarioExecutor;
import executor.service.service.scenarios.ScenarioProvider;
import executor.service.service.scenarios.ScenarioSourceListener;
import executor.service.service.scenarios.impl.ScenarioSourceListenerImpl;
import executor.service.service.stepexecution.StepExecutionSleep;
import executor.service.service.stepexecution.impl.StepExecutionSleepImpl;
import executor.service.service.webdriver.WebDriverInitializer;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ObjectFactoryTest {

  private static final ObjectFactory factory = new ObjectFactoryImpl();

  @Test
  public void isSingleton() {

    StepExecutionSleep stepExecutionSleep = factory.create(StepExecutionSleep.class);
    StepExecutionSleep stepExecutionSleep2 = factory.create(StepExecutionSleep.class);
    assertSame(stepExecutionSleep, stepExecutionSleep2);

    WebDriverInitializer webDriver1 = factory.create(WebDriverInitializer.class);
    WebDriverInitializer webDriver2 = factory.create(WebDriverInitializer.class);
    assertSame(webDriver1, webDriver2);

    ScenarioExecutor scenarioExecutor = factory.create(ScenarioExecutor.class);
    ScenarioExecutor scenarioExecutor2 = factory.create(ScenarioExecutor.class);
    assertSame(scenarioExecutor, scenarioExecutor2);

  }

  @Test
  public void createInterfaceWithoutDI() {

    StepExecutionSleep stepExecutionSleep = factory.create(StepExecutionSleep.class);

    assertNotNull(stepExecutionSleep);

    assertEquals(stepExecutionSleep.getClass(), StepExecutionSleepImpl.class);

  }

  @Test
  public void createScenarioSourceListenerWithDI() {

    ScenarioSourceListener service = factory.create(ScenarioSourceListener.class);

    assertNotNull(service);
    assertEquals(service.getClass(), ScenarioSourceListenerImpl.class);
    ScenarioProvider provider = null;
    try {
      Field fieldProvider = service.getClass().getDeclaredField("provider");
      fieldProvider.setAccessible(true);
      provider = (ScenarioProvider) fieldProvider.get(service);
    } catch (Exception e) {
    }

    assertNotNull(provider);

  }

  @Test
  public void createParallelFlowExecutorServiceWithDI() {
    ParallelFlowExecutorService service = factory.create(ParallelFlowExecutorService.class);

    assertNotNull(service);
    assertEquals(service.getClass(), ParallelFlowExecutorServiceImpl.class);

    System.out.println();
  }

  @Test
  public void createPairGeneratorService() {
    ExecutableScenarioComposer defaultExecutableScenarioComposer = factory.create(ExecutableScenarioComposer.class);

    assertNotNull(defaultExecutableScenarioComposer);
    assertEquals(defaultExecutableScenarioComposer.getClass(), ExecutableScenarioComposerImpl.class);
    System.out.println();
  }
}