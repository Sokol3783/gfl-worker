package executor.service.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProxyQueue {
    private BlockingQueue<ProxyConfigHolder> proxies;

    public ProxyQueue() {
        this.proxies = new LinkedBlockingDeque<>();
    }

    public void addProxy(ProxyConfigHolder proxy) {
        proxies.add(proxy);
    }

    public ProxyConfigHolder getProxy() throws InterruptedException {
        return proxies.take();
    }
}