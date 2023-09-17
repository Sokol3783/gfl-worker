package executor.service.DIFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import executor.service.service.*;

import java.lang.reflect.Field;

import executor.service.service.parallelflowexecutor.ParallelFlowExecutorService;
import executor.service.service.scenarios.impl.ScenarioSourceListenerImpl;
import executor.service.service.scenarios.ScenarioExecutor;
import executor.service.service.scenarios.ScenarioProvider;
import executor.service.service.scenarios.ScenarioSourceListener;
import executor.service.service.stepexecution.impl.StepExecutionSleepImpl;
import executor.service.service.stepexecution.StepExecutionSleep;
import executor.service.service.webdriver.WebDriverInitializer;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

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
    assertEquals(service.getClass(), ParallelFlowExecutorService.class);

    WebDriver webDriver;
    ExecutionService executionService;
    try {
      Field fieldWebDriver = service.getClass().getDeclaredField("webDriver");
      Field fieldExecutionService = service.getClass().getDeclaredField("executionService");
      fieldWebDriver.setAccessible(true);
      fieldExecutionService.setAccessible(true);
      executionService = (ExecutionService) fieldExecutionService.get(service);
      webDriver = (WebDriver) fieldWebDriver.get(service);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    assertNotNull(webDriver);
    assertNotNull(executionService);
  }
}