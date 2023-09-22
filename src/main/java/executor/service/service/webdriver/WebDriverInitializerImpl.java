package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;


public class WebDriverInitializerImpl implements WebDriverInitializer {

    @Override
    public WebDriver getInstance(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        String host = proxyConfigHolder.getProxyNetworkConfig().getHostname();
        Integer port = proxyConfigHolder.getProxyNetworkConfig().getPort();
        String username = proxyConfigHolder.getProxyCredentials().getUsername();
        String password = proxyConfigHolder.getProxyCredentials().getPassword();
        File proxyPlugin = null;
        ChromeOptions options = configureChromeOptions(webDriverConfig);

        if (!Strings.isNullOrEmpty(host)) {
            if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
                proxyPlugin = ChromeProxyPlugin.generate(host, port, username, password);
                options.addExtensions(proxyPlugin);
            } else {
                options.addArguments("--proxy-server=" + host + ":" + port);
            }
        }

        ChromeDriver driver = new ChromeDriver(options);
        if (proxyPlugin != null) {
            proxyPlugin.delete();
        }

        return driver;
    }

    private ChromeOptions configureChromeOptions(WebDriverConfig webDriverConfig) {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setBinary(PropertiesConstants.CHROME_EXECUTABLE);
        chromeOptions.setBrowserVersion(PropertiesConstants.CHROME_VERSION);
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setBinary(webDriverConfig.getWebDriverExecutable());
        chromeOptions.addArguments("user-agent=" + webDriverConfig.getUserAgent());
        chromeOptions.setImplicitWaitTimeout(Duration.ofMillis(webDriverConfig.getImplicitlyWait()));
        chromeOptions.setPageLoadTimeout(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));
        System.setProperty("webdriver.chrome.driver", webDriverConfig.getWebDriverExecutable());

        return chromeOptions;
    }
}
