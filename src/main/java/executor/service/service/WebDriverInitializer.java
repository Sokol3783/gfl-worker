package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import org.openqa.selenium.WebDriver;

public interface WebDriverInitializer {
    WebDriver getInstance(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder);
}
