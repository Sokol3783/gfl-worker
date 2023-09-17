package executor.service.service.parallelflowexecutor.impls;

import executor.service.queue.ProxyQueue;
import executor.service.service.parallelflowexecutor.Task;
import executor.service.service.proxy.ProxySourcesClient;

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
