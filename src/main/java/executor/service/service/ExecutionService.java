package executor.service.service;

import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(Scenario scenario, WebDriver webDriver);

}
