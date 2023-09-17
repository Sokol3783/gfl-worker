package executor.service.service.scenarios.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import executor.service.model.Scenario;
import executor.service.service.scenarios.ScenarioProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JSONFileScenarioProvider implements ScenarioProvider {

    private static final Logger log = LoggerFactory.getLogger(ScenarioProvider.class);
    private static final String FILE_NAME = "/scenarios.json";

    @Override
    public List<Scenario> readScenarios() {
        List<Scenario> scenarios = null;
        try (InputStream inputStream = getClass().getResourceAsStream(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            scenarios = parseScenariosFromJson(reader);
        } catch (IOException e) {
            log.error("Cannot read file {}. Reason: {}", FILE_NAME, e.getMessage(), e);
        }
        return scenarios;
    }

    private List<Scenario> parseScenariosFromJson(BufferedReader reader) {
        Type listType = new TypeToken<List<Scenario>>() {}.getType();
        return new Gson().fromJson(reader, listType);
    }
}