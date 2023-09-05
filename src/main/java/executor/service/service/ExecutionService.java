package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

import java.util.concurrent.BlockingQueue;

public interface ExecutionService {

    void execute(BlockingQueue<Scenario> scenarios, BlockingQueue<ProxyConfigHolder> proxies);

}
