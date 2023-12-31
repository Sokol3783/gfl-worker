package executor.service.service.proxy;

import executor.service.handler.ProxyHandler;
import executor.service.queue.ProxyQueue;
import executor.service.service.parallelflowexecutor.impls.publishers.ProxyPublisher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ProxyPublisherTest {
    private ProxyQueue proxyQueue;
    private ProxySourcesClient proxyClient;
    private ProxyPublisher proxyPublisher;

    @BeforeEach
    void setUp() {
        proxyQueue = mock(ProxyQueue.class);
        proxyClient = mock(ProxySourcesClient.class);
        proxyPublisher = new ProxyPublisher(proxyQueue, proxyClient);
    }

    @Test
    void testRun() {
        doNothing().when(proxyClient).execute(any(ProxyHandler.class));

        proxyPublisher.run();

        verify(proxyClient).execute(any(ProxyHandler.class));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(proxyClient);
    }
}