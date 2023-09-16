package executor.service;

import executor.service.config.di.FactoryForDI;
import executor.service.config.di.ApplicationContext;
import executor.service.service.ParallelFlowExecutorService;
import executor.service.service.parallel.ParallelFlowExecutorServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main( String[] args ) {
        ApplicationContext context = FactoryForDI.run(
                getPackageName(),
                new HashMap<>(Map.of(ParallelFlowExecutorService.class, ParallelFlowExecutorServiceImpl.class)));
        ParallelFlowExecutorService service = context.getObject(ParallelFlowExecutorService.class);
        service.execute();
    }

    private static String getPackageName() {
        Package currentPackage = App.class.getPackage();
        return currentPackage.getName();
    }
}
