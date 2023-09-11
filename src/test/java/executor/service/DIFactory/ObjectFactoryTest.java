package executor.service.DIFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import executor.service.service.ScenarioExecutor;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.StepExecutionSleep;
import executor.service.service.WebDriverInitializer;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class ObjectFactoryTest {

  private static final ObjectFactory factory = ObjectFactoryImpl.getInstance();

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

    assertEquals(stepExecutionSleep.getClass(), StepExecutionSleep.class);


  }

  @Test
  public void createScenarioSourceListenerWithDI() {

    ScenarioSourceListener service = factory.create(ScenarioSourceListener.class);

    assertNotNull(service);

    assertEquals(service.getClass(), ScenarioSourceListener.class);

    ScenarioProvider provider = null;
    try {
      Field fieldProvider = service.getClass().getDeclaredField("provider");
      provider = (ScenarioProvider) fieldProvider.get(ScenarioProvider.class);
    } catch (Exception e) {
    }

    assertNotNull(provider);

  }

}