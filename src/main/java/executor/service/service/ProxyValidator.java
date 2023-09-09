package executor.service.service;

import executor.service.model.ProxyConfigHolder;

public interface ProxyValidator {

    Boolean isValid(ProxyConfigHolder proxyConfigHolder);
}
