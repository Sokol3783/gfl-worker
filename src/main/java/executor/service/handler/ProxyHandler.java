package executor.service.handler;

import executor.service.model.proxy.ProxyConfigHolder;

/**
 * The {@code ProxyHandler} functional interface defines a contract for handling proxy configurations
 * received during a specific process or operation.
 * <p>
 * Implementing classes or lambda expressions should provide the logic for processing received proxy
 * configurations by implementing the {@link #onProxyReceived(ProxyConfigHolder)} method.
 *
 * @author Yurii Kotsiuba
 */
public interface ProxyHandler {
    /**
     * Handles a received proxy configuration.
     *
     * @param proxy The proxy configuration received for processing.
     */
    void onProxyReceived(ProxyConfigHolder proxy);
}