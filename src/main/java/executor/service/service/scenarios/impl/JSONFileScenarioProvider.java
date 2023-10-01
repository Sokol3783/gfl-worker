package executor.service.service.scenarios.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import executor.service.model.scenario.Scenario;
import executor.service.model.scenario.StepTypes;
import executor.service.service.scenarios.ScenarioProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code JSONFileScenarioProvider} class is an implementation of the {@link ScenarioProvider}
 * interface. It is responsible for reading scenarios from a JSON file and providing them as a list
 * of {@link Scenario} objects.
 * @author Yurii Kotsiuba
 * @see ScenarioProvider
 * @see Scenario
 */
public class JSONFileScenarioProvider implements ScenarioProvider {

    private static final Logger log = LoggerFactory.getLogger(ScenarioProvider.class);
    private static final String FILE_NAME = "/scenarios.json";

    /**
     * Reads scenarios from a JSON file and returns them as a list of {@link Scenario} objects.
     *
     * @return A list of scenarios read from the JSON file.
     */
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