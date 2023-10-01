package executor.service.service.proxy.impl;

import executor.service.handler.ProxyHandler;
import executor.service.handler.TerminatorListener;
import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.service.proxy.ProxyProvider;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.proxy.ProxyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The {@code ProxySourcesClientImpl} class is an implementation of the {@link ProxySourcesClient}
 * interface. It is responsible for executing the retrieval and handling of proxy configurations
 * from a {@link ProxyProvider}. Proxies are continuously processed in a loop with a specified delay.
 * <p>
 * The proxy configurations are retrieved using the specified provider and validated using the validator.
 * Valid proxies are then processed using the provided handler.
 * <p>
 *
 * @author Yurii Kotsiuba
 * @see ProxySourcesClient
 * @see ProxyProvider
 * @see ProxyValidator
 * @see ProxyConfigHolder
 */
public class ProxySourcesClientImpl implements ProxySourcesClient {

    private static final Logger log = LoggerFactory.getLogger(ProxySourcesClient.class);
    private static final long DELAY = 1000;
    private final ProxyProvider provider;
    private final ProxyValidator validator;

    /**
     * Constructs a new {@code ProxySourcesClientImpl} with the specified proxy provider and validator.
     *
     * @param provider  The provider responsible for providing proxy configurations.
     * @param validator The validator used to validate proxy configurations.
     */
    public ProxySourcesClientImpl(ProxyProvider provider, ProxyValidator validator) {
        this.provider = provider;
        this.validator = validator;
    }

    /**
     * Executes the proxy handling process with a default terminator condition that always returns false.
     * It retrieves proxy configurations from the provider, validates them, and continuously handles proxies
     * using the provided handler.
     *
     * @param handler The proxy handler responsible for processing received proxy configurations.
     */
    @Override
    public void execute(ProxyHandler handler) {
        execute(handler, () -> false);
    }

    /**
     * Executes the proxy handling process with the option to specify a custom terminator condition.
     * It retrieves proxy configurations from the provider, validates them, and continuously handles proxies
     * using the provided handler. The processing can be terminated based on the custom terminator condition.
     *
     * @param handler    The proxy handler responsible for processing received proxy configurations.
     * @param terminator The terminator listener to control the termination of the proxy processing.
     */
    public void execute(ProxyHandler handler, TerminatorListener terminator) {
        List<ProxyConfigHolder> proxies = provider.readProxy();
        validateList(proxies);
        listen(proxies, handler, terminator);
    }

    /**
     * Validates the list of proxy configurations to ensure it is not null or empty. If the list
     * is invalid, an error is logged, and an {@link IllegalArgumentException} is thrown.
     *
     * @param proxies The list of proxy configurations to validate.
     * @throws IllegalArgumentException if the list is null or empty.
     */
    private void validateList(List<ProxyConfigHolder> proxies) {
        if(proxies == null || proxies.isEmpty()) {
            log.error("Bad proxy list.");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }

    /**
     * Listens for proxy configurations in a loop and passes them to the provided handler. The proxy
     * configurations are processed continuously with a delay between each processing cycle.
     *
     * @param proxies     The list of proxy configurations to listen to.
     * @param handler     The proxy handler responsible for processing received proxy configurations.
     * @param terminator  The terminator listener to control the termination of the proxy processing.
     */
    private void listen(List<ProxyConfigHolder> proxies, ProxyHandler handler, TerminatorListener terminator) {
        int currentIndex = 0;
        while(!terminator.shouldTerminate()){
            ProxyConfigHolder proxy = proxies.get(currentIndex);
            if(validator.isValid(proxy)) {
                handler.onProxyReceived(proxy);
            }
            handler.onProxyReceived(proxy);
            currentIndex = (currentIndex + 1) % proxies.size();
            sleep();
        }
    }

    /**
     * Sleeps for a specified delay, allowing for a controlled pace of scenario processing.
     */
    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}