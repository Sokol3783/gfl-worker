package executor.service.service.parallelflowexecutor.impls.publishers;


import executor.service.queue.ProxyQueue;
import executor.service.service.parallelflowexecutor.Operatable;
import executor.service.service.proxy.ProxySourcesClient;

public class ProxyPublisher implements Operatable {
    private final ProxyQueue proxyQueue;
    private final ProxySourcesClient proxyClient;

    public ProxyPublisher(ProxyQueue proxyQueue, ProxySourcesClient proxyClient) {
        this.proxyQueue = proxyQueue;
        this.proxyClient = proxyClient;
    }

    @Override
    public void run() {
        proxyClient.execute(proxyQueue::addProxy);
    }
}
