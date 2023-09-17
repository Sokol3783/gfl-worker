package executor.service.service.proxy;

import executor.service.model.proxy.ProxyConfigHolder;

public interface ProxyValidator {

    Boolean isValid(ProxyConfigHolder proxyConfigHolder);
}
