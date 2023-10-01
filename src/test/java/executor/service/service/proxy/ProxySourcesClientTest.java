package executor.service.service.proxy;

import executor.service.handler.ProxyHandler;
import executor.service.handler.TerminatorListener;
import executor.service.model.proxy.ProxyConfigHolder;
import executor.service.service.proxy.impl.ProxySourcesClientImpl;
import executor.service.util.TestTerminator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ProxySourcesClientTest {

    private ProxySourcesClientImpl client;

    private ProxyProvider provider;

    private ProxyHandler handler;

    private ProxyValidator validator;

    @BeforeEach
    public void setUp() {
        provider = mock(ProxyProvider.class);
        handler = mock(ProxyHandler.class);
        validator = mock(ProxyValidator.class);
        client = new ProxySourcesClientImpl(provider, validator);
    }

    @Test
    public void testExecute_numberOfTimes() {
        int maxIterations = 5;
        TerminatorListener terminator = new TestTerminator(maxIterations);

        given(provider.readProxy()).willReturn(prepareProxyList());
        given(validator.isValid(any(ProxyConfigHolder.class))).willReturn(true);

        client.execute(handler, terminator);

        verify(handler, times(maxIterations)).onProxyReceived(any(ProxyConfigHolder.class));
        verify(provider).readProxy();
    }

    @Test
    public void testExecute_notValidProxies() {
        int maxIterations = 5;
        TerminatorListener terminator = new TestTerminator(maxIterations);

        given(provider.readProxy()).willReturn(prepareProxyList());
        given(validator.isValid(any(ProxyConfigHolder.class))).willReturn(false);

        client.execute(handler, terminator);

        verifyNoInteractions(handler);
        verify(provider).readProxy();
    }

    @Test
    public void testExecute_throwException_whenNullList() {
        given(provider.readProxy()).willReturn(null);

        assertThrows(IllegalArgumentException.class, ()-> client.execute(handler, () -> false));
        verify(provider).readProxy();
    }

    @Test
    public void testExecute_throwException_whenEmptyList() {
        List<ProxyConfigHolder> emptyProxyList = new ArrayList<>();

        given(provider.readProxy()).willReturn(emptyProxyList);

        assertThrows(IllegalArgumentException.class, ()-> client.execute(handler, () -> false));
        verify(provider).readProxy();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(provider);
        verifyNoMoreInteractions(handler);
    }

    private List<ProxyConfigHolder> prepareProxyList() {
        return Arrays.asList(
                new ProxyConfigHolder(),
                new ProxyConfigHolder(),
                new ProxyConfigHolder()
        );
    }
}