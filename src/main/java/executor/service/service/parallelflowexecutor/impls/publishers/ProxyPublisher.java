package executor.service.service.parallelflowexecutor.impls.publishers;


import executor.service.queue.ProxyQueue;
import executor.service.service.parallelflowexecutor.Operatable;
import executor.service.service.proxy.ProxySourcesClient;

/**
 * The {@code ProxyPublisher} class is responsible for publishing proxy information to a proxy queue
 * by utilizing a provided {@link ProxySourcesClient}.
 * <p>
 * This class implements the {@link Operatable} interface, allowing it to be executed in a separate
 * thread or context.
 * <p>
 *
 * @author Yurii Kotsiuba
 * @see Operatable
 * @see ProxyQueue
 * @see ProxySourcesClient
 */
public class ProxyPublisher implements Operatable {
    private final ProxyQueue proxyQueue;
    private final ProxySourcesClient proxyClient;

    /**
     * Constructs a new {@code ProxyPublisher} with the specified proxy queue and client.
     *
     * @param proxyQueue  The proxy queue to which proxy information will be published.
     * @param proxyClient The client responsible for executing proxy information publication.
     */
    public ProxyPublisher(ProxyQueue proxyQueue, ProxySourcesClient proxyClient) {
        this.proxyQueue = proxyQueue;
        this.proxyClient = proxyClient;
    }

    /**
     * Runs the proxy information publication process by invoking the execute
     * method with the proxy queue's {@code addProxy} method reference.
     */
    @Override
    public void run() {
        proxyClient.execute(proxyQueue::addProxy);
    }
}
