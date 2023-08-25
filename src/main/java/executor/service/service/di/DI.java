package executor.service.service.di;

import executor.service.service.ParalleFlowExecutorService;
import executor.service.service.ScenarioSourceListener;
import executor.service.service.impl.ScenarioSourceListenerImpl;

import java.util.HashMap;
import java.util.Map;

public class DI {

    public static Object run() {
        ApplicationContext context = Application.run("executor.service", new HashMap<>(Map.of(ScenarioSourceListener.class, ScenarioSourceListenerImpl.class)));
        return context.getObject(ScenarioSourceListener.class);
    }

    public static void main(String[] args) {
        Object run = DI.run();
        System.out.println();
    }
}
