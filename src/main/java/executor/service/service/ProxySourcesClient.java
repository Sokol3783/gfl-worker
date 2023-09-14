package executor.service.service;

import executor.service.handler.ProxyHandler;
import executor.service.model.ProxyConfigHolder;

public interface ProxySourcesClient {

    void execute(ProxyHandler handler);

}
