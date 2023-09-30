package executor.service.service.proxy;

import executor.service.model.proxy.ProxyConfigHolder;

import java.util.List;

public interface ProxyProvider {
    List<ProxyConfigHolder> readProxy();
}
