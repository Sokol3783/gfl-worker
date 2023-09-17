package executor.service.service.proxy;

import executor.service.model.ProxyConfigHolder;

public interface ProxyValidator {

    Boolean isValid(ProxyConfigHolder proxyConfigHolder);
}
