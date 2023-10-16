package executor.service.service.webdriver;

import com.google.common.base.Strings;
import executor.service.model.configs.WebDriverConfig;
import executor.service.model.proxy.ProxyConfigHolder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;


public class WebDriverInitializerImpl implements WebDriverInitializer {

    private final WebDriverConfig webDriverConfig;
    private final String CHROME_VERSION = System.getProperty("CHROME_VERSION");


    public WebDriverInitializerImpl(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    @Override
    public WebDriver getInstance(ProxyConfigHolder proxyConfigHolder) {
        //will fall if ProxyNetworkConfig or ProxyCredentials are themselves null
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

        ChromeDriver driver = new ChromeDriver();
        if (proxyPlugin != null) {
            boolean delete = proxyPlugin.delete();
            if (!delete){
                System.out.println(proxyPlugin.getName() + " wasn't delete!");
            }
        }

        return driver;
    }

    private ChromeOptions configureChromeOptions(WebDriverConfig webDriverConfig) {
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setBinary(System.getenv("CHROME_PATH"));
        chromeOptions.setBrowserVersion(CHROME_VERSION);
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("user-agent=" + webDriverConfig.getUserAgent());
        chromeOptions.setImplicitWaitTimeout(Duration.ofMillis(webDriverConfig.getImplicitlyWait()));
        chromeOptions.setPageLoadTimeout(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));
        System.setProperty("webdriver.chrome.driver", System.getenv("WEB_DRIVER_PATH"));

        return chromeOptions;
    }
}
