package executor.service.model.proxy;

import java.util.Objects;

public class ProxyConfigHolder {

    private ProxyNetworkConfig proxyNetworkConfig;
    private ProxyCredentials proxyCredentials;

    public ProxyConfigHolder() {
    }

    public ProxyConfigHolder(ProxyNetworkConfig proxyNetworkConfig,
                             ProxyCredentials proxyCredentials) {
        this.proxyNetworkConfig = proxyNetworkConfig;
        this.proxyCredentials = proxyCredentials;
    }

    public ProxyNetworkConfig getProxyNetworkConfig() {
        return proxyNetworkConfig;
    }

    public void setProxyNetworkConfig(ProxyNetworkConfig proxyNetworkConfig) {
        this.proxyNetworkConfig = proxyNetworkConfig;
    }

    public ProxyCredentials getProxyCredentials() {
        return proxyCredentials;
    }

    public void setProxyCredentials(ProxyCredentials proxyCredentials) {
        this.proxyCredentials = proxyCredentials;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProxyConfigHolder that = (ProxyConfigHolder) obj;
        return this.proxyNetworkConfig.equals(that.proxyNetworkConfig)
                && this.proxyCredentials.equals(that.proxyCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proxyCredentials, proxyNetworkConfig);
    }
}
