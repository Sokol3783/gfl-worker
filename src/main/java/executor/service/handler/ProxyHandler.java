package executor.service.handler;

import executor.service.model.ProxyConfigHolder;

public interface ProxyHandler {
    void onProxyReceived(ProxyConfigHolder proxy);
}
