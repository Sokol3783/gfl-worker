package executor.service.config.di;

import executor.service.App;
import executor.service.service.ParalleFlowExecutorService;

import java.util.HashMap;
import java.util.Map;

public class DIRunner {

    public void run() {
        ApplicationContext context = Application.run(
                getPackageName(),
                new HashMap<>(Map.of(ParalleFlowExecutorService.class, ParalleFlowExecutorService.class)));
        ParalleFlowExecutorService service = context.getObject(ParalleFlowExecutorService.class);
        service.execute();
    }

    private static String getPackageName() {
        Package currentPackage = App.class.getPackage();
        return currentPackage.getName();
    }
}
