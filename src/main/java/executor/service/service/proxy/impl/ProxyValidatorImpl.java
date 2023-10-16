package executor.service.service.proxy.impl;

import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.service.proxy.ProxyValidator;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class ProxyValidatorImpl implements ProxyValidator {
    private final static String TARGET_URL = "http://www.google.com/";
    private final static int CONNECTION_TIMEOUT = 10000;

    public Boolean isValid(ProxyConfigHolder proxyConfigHolder) {
        int responseCode = 0;

        try {
            CredentialsProvider credentialsProvider = getCredentialsProvider(proxyConfigHolder);
            CloseableHttpClient httpClient = getHttpClient(proxyConfigHolder, credentialsProvider);

            HttpGet httpGet = new HttpGet(TARGET_URL);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            responseCode = response.getStatusLine().getStatusCode();

            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode == HttpStatus.SC_OK;
    }

    private CredentialsProvider getCredentialsProvider(ProxyConfigHolder proxyConfig) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(proxyConfig.getProxyNetworkConfig().getHostname(),
                        proxyConfig.getProxyNetworkConfig().getPort()),
                new UsernamePasswordCredentials(proxyConfig.getProxyCredentials().getUsername(),
                        proxyConfig.getProxyCredentials().getPassword()));
        return credentialsProvider;
    }

    private CloseableHttpClient getHttpClient(ProxyConfigHolder proxyConfig, CredentialsProvider credentials) {
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credentials)
                .setProxy(new HttpHost(proxyConfig.getProxyNetworkConfig().getHostname(),
                        proxyConfig.getProxyNetworkConfig().getPort()))
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(CONNECTION_TIMEOUT)
                        .setSocketTimeout(CONNECTION_TIMEOUT)
                        .build())
                .build();
    }
}
