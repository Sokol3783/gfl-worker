package executor.service.service;

import executor.service.handler.ProxyHandler;

/**
 * The {@code ProxySourcesClient} interface defines a contract for clients responsible for executing
 * the retrieval and handling of proxy configurations using a {@link ProxyHandler}.
 * <p>
 * Implementing classes or objects that implement this interface should provide the logic for retrieving
 * and processing proxy configurations using the specified {@link ProxyHandler}.
 * <p>
 * The actual behavior of retrieving and handling proxy configurations may vary based on the implementation.
 *
 * @author Yurii Kotsiuba
 * @see ProxyHandler
 */
public interface ProxySourcesClient {
    /**
     * Executes the retrieval and handling of proxy configurations using the provided {@link ProxyHandler}.
     *
     * @param handler The proxy handler responsible for processing received proxy configurations.
     */
    void execute(ProxyHandler handler);
}