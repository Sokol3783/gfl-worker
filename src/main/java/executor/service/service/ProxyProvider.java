package executor.service.service;

import executor.service.model.ProxyConfigHolder;

import java.util.List;

public interface ProxyProvider {
    List<ProxyConfigHolder> readProxy();
}
