package executor.service.service;

import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

public class ParalleFlowExecutorService {

    private final WebDriver webDriver;
    private final ExecutionService executionService;

    public ParalleFlowExecutorService(WebDriver webDriver, ExecutionService executionService) {
        this.webDriver = webDriver;
        this.executionService = executionService;
    }

    public void runAllScenario(Scenario scenario) {

    }
}
