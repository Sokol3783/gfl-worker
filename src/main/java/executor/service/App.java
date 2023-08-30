package executor.service;

import executor.service.config.di.Application;
import executor.service.config.di.ApplicationContext;
import executor.service.service.ParalleFlowExecutorService;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main( String[] args ) {
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
