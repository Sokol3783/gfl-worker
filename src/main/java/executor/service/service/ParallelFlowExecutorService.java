package executor.service.service;

import org.openqa.selenium.WebDriver;

public class ParallelFlowExecutorService {

    private  WebDriver webDriver;
    private  ExecutionService executionService;

    public ParallelFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
    }

    public void run() {

    }
}
