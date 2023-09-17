package executor.service.service.impl;

import executor.service.model.ProxyQueue;
import executor.service.service.ProxySourcesClient;
import executor.service.service.Task;

public class ProxyListenerTask implements Task {
    private final ProxyQueue proxyQueue;
    private final ProxySourcesClient proxyClient;

    public ProxyListenerTask(ProxyQueue proxyQueue, ProxySourcesClient proxyClient) {
        this.proxyQueue = proxyQueue;
        this.proxyClient = proxyClient;
    }

    @Override
    public void run() {
        proxyClient.execute(proxyQueue::addProxy);
    }
}
