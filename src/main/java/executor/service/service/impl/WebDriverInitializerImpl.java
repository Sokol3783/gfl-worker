package executor.service.service.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import executor.service.service.WebDriverInitializer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WebDriverInitializerImpl implements WebDriverInitializer {
    @Override
    public WebDriver getInstance(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        ChromeDriver driver = new ChromeDriver(getChromeOptions(webDriverConfig, proxyConfigHolder));
        new File("proxy_auth_plugin.zip").delete();
        return driver;
    }

    private ChromeOptions getChromeOptions(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
        String host = proxyConfigHolder.getProxyNetworkConfig().getHostname();
        Integer port = proxyConfigHolder.getProxyNetworkConfig().getPort();
        String username = proxyConfigHolder.getProxyCredentials().getUsername();
        String password = proxyConfigHolder.getProxyCredentials().getPassword();
        ChromeOptions chromeOptions = new ChromeOptions();

        if (username != null && !username.isBlank()) {
            chromeOptions.addExtensions(createProxyPlugin(host, port, username, password));
        } else if (host != null && !host.isBlank()) {
            chromeOptions.addArguments("--proxy-server=" + host + ":" + port);
        }

        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setBinary(webDriverConfig.getWebDriverExecutable());
        chromeOptions.addArguments("user-agent=" + webDriverConfig.getUserAgent());
        chromeOptions.setImplicitWaitTimeout(Duration.ofMillis(webDriverConfig.getImplicitlyWait()));
        chromeOptions.setPageLoadTimeout(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));

        return chromeOptions;
    }

    private File createProxyPlugin(String host, int port, String username, String password) {
        String manifestJson = generateManifestJson();
        String backgroundJs = generateBackgroundJs(host, port, username, password);

        try {
            File zipFile = new File("proxy_auth_plugin.zip");
            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zipOS = new ZipOutputStream(fos)) {
                writeToZipFile(zipOS, "manifest.json", manifestJson);
                writeToZipFile(zipOS, "background.js", backgroundJs);
            }
            return zipFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateManifestJson() {
        return """
        {
          "version": "1.0.0",
          "manifest_version": 2,
          "name": "Chrome Proxy",
          "permissions": [
            "proxy",
            "tabs",
            "unlimitedStorage",
            "storage",
            "<all_urls>",
            "webRequest",
            "webRequestBlocking"
          ],
          "background": {
            "scripts": ["background.js"]
          },
          "minimum_chrome_version": "22.0.0"
        }
        """;
    }

    private String generateBackgroundJs(String host, int port, String username, String password) {
        return String.format("""
        var config = {
          mode: "fixed_servers",
          rules: {
            singleProxy: {
              scheme: "http",
              host: "%s",
              port: %d
            },
            bypassList: ["localhost"]
          }
        };
    
        chrome.proxy.settings.set({value: config, scope: "regular"}, function() {});
    
        function callbackFn(details) {
          return {
            authCredentials: {
              username: "%s",
              password: "%s"
            }
          };
        }
    
        chrome.webRequest.onAuthRequired.addListener(
          callbackFn,
          {urls: ["<all_urls>"]},
          ['blocking']
        );
        """, host, port, username, password);
    }

    private void writeToZipFile(ZipOutputStream zipStream, String entryName, String content) throws IOException {
        zipStream.putNextEntry(new ZipEntry(entryName));
        zipStream.write(content.getBytes());
        zipStream.closeEntry();
    }
}