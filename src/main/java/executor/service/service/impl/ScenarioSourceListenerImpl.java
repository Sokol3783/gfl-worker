package executor.service.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import executor.service.model.Scenario;
import executor.service.service.ScenarioSourceListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {
    // TODO: introduce proper filename constant holding
    public static final String FILE_NAME = "/scenarios.json";

    @Override
    public void execute() {
        try (InputStream inputStream = getClass().getResourceAsStream(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<Scenario> scenarios = parseScenariosFromJson(reader);

            for (Scenario scenario : scenarios) {
                processScenario(scenario);
            }

        } catch (IOException e) {
            // TODO: handle exceptions
            System.err.println(e.getMessage());
        }
    }

    private List<Scenario> parseScenariosFromJson(BufferedReader reader) {
        Type listType = new TypeToken<List<Scenario>>() {}.getType();
        return new Gson().fromJson(reader, listType);
    }

    private void processScenario(Scenario scenario) {
        // TODO: add logic here
    }
}
