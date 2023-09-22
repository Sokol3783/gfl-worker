package executor.service.service.webdriver;

import executor.service.model.configs.WebDriverConfig;
import executor.service.model.proxy.ProxyConfigHolder;
import org.openqa.selenium.WebDriver;

public interface WebDriverInitializer {
    WebDriver getInstance(ProxyConfigHolder proxyConfigHolder);
}
