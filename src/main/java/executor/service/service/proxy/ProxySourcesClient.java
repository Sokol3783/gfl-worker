package executor.service.service.proxy;

import executor.service.handler.ProxyHandler;
import executor.service.model.ProxyConfigHolder;

public interface ProxySourcesClient {

    void execute(ProxyHandler handler);

}
