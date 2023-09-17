package executor.service;

import executor.service.DIFactory.ObjectFactoryImpl;
import executor.service.service.ParallelFlowExecutorService;

public class App {

  public static void main(String[] args) {

    private static String getPackageName() {
        Package currentPackage = App.class.getPackage();
        return currentPackage.getName();
    }
}
