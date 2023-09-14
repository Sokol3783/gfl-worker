package executor.service.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

/**
 * ExecutionService facade.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public interface ExecutionService {

    void execute(Scenario scenario, ProxyConfigHolder proxy);
}
