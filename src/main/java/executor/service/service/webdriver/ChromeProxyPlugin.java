package executor.service.service.webdriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ChromeProxyPlugin {

    private static final String MANIFEST_JSON= """
            {
              "version": "0.0.0",
              "name": "Chrome Proxy",
              "manifest_version": 1,
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
              "minimum_chrome_version": "21.0.0"
            }
            """;
    private static final Random RANDOM = new Random();

    private ChromeProxyPlugin(){
    }

    public static File generate(String host, int port, String username, String password) {
        String backgroundJs = generateBackgroundJs(host, port, username, password);

        try {
            File zipFile = new File(generateRandomName());
            try (FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zipOS = new ZipOutputStream(fos)) {
                writeToZipFile(zipOS, "manifest.json", MANIFEST_JSON);
                writeToZipFile(zipOS, "background.js", backgroundJs);
            }
            return zipFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateBackgroundJs(String host, int port, String username, String password) {
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

    public static String generateRandomName() {
        return "proxy_auth_plugin" +  RANDOM.nextInt() * 100000000 + ".zip";
    }

    private static void writeToZipFile(ZipOutputStream zipStream, String entryName, String content) throws IOException {
        zipStream.putNextEntry(new ZipEntry(entryName));
        zipStream.write(content.getBytes());
        zipStream.closeEntry();
    }
}
