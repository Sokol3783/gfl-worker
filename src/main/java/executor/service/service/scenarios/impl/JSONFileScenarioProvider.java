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

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(StepTypes.class, new StepTypeAdapter<>()); // Регистрируем адаптер для StepType
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Scenario>>(){}.getType();
        return gson.fromJson(reader, listType);
    }

    class StepTypeAdapter<StepType> implements JsonSerializer<StepTypes>, JsonDeserializer<StepTypes> {
        @Override
        public JsonElement serialize(StepTypes enumValue, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(enumValue.toString());
        }

        @Override
        public StepTypes deserialize(JsonElement json, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                return Arrays.stream(StepTypes.values()).filter(s -> s.getName().compareToIgnoreCase(json.getAsString()) == 0).findFirst().orElseThrow();

            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Invalid ENUM value: " + json.getAsString(), e);
            }
        }
    }



}