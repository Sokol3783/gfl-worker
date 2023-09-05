package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

import java.util.Queue;

public interface ExecutionService {

    void execute(Queue<Scenario> scenarios, Queue<ProxyConfigHolder> proxies);

}
