package executor.service.service.proxy;

import executor.service.model.proxy.ProxyConfigHolder;

import java.util.List;

/**
 * The {@code ProxyProvider} interface defines a contract for classes that provide proxy configurations.
 * Classes implementing this interface are responsible for reading and providing a list of {@link ProxyConfigHolder} objects.
 * <p>
 * Implementing classes are expected to read proxy configurations from a specific source, such as a file, database, or network,
 * and return them as a list of proxy configuration holders.
 * <p>
 * Implementing classes should also handle any exceptions or errors that may occur during the proxy configuration retrieval process.
 *
 * @author Yurii Kotsiuba
 * @see ProxyConfigHolder
 */
public interface ProxyProvider {
    /**
     * Reads proxy configurations from a specific source and returns them as a list of {@link ProxyConfigHolder} objects.
     *
     * @return A list of proxy configurations read from the source.
     */
    List<ProxyConfigHolder> readProxy();
}