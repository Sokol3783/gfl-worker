package executor.service.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import executor.service.model.Scenario;
import executor.service.service.ParalleFlowExecutorService;
import executor.service.service.ScenarioProvider;
import executor.service.service.ScenarioSourceListener;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    private final ScenarioProvider provider;
    private final ParalleFlowExecutorService service;
    private final WebDriver webDriver;
    private static final Logger log = LoggerFactory.getLogger(ScenarioProvider.class);

    public ScenarioSourceListenerImpl(ScenarioProvider provider, ParalleFlowExecutorService service, WebDriver webDriver) {
        this.provider = provider;
        this.service = service;
        this.webDriver = webDriver;
    }

    @Override
    public void execute() {
        List<Scenario> scenarios = provider.readScenarios();
        validateScenarios(scenarios);
        scenarios.forEach(scenario -> processScenario(scenario));
    }

    private void validateScenarios(List<Scenario> scenarios) {
        if(scenarios == null || scenarios.isEmpty()) {
            log.error("Bad scenarios list.");
            throw new IllegalArgumentException("List cannot be null or empty");
        }
    }

    private void processScenario(Scenario scenario) {
        // TODO: add logic here
        log.info("Adding scenario {}.", scenario);
    }
}
