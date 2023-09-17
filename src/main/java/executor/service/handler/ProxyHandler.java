package executor.service.handler;

import executor.service.model.proxy.ProxyConfigHolder;

public interface ProxyHandler {
    void onProxyReceived(ProxyConfigHolder proxy);
}
