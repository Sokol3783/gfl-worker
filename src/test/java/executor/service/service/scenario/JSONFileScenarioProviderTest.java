package executor.service.service.scenario;

import executor.service.model.scenario.Scenario;
import executor.service.service.scenarios.ScenarioProvider;
import executor.service.service.scenarios.impl.JSONFileScenarioProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONFileScenarioProviderTest {

    private static final int EXPECTED_LIST_SIZE = 5;
    private ScenarioProvider provider;
    private List<Scenario> scenarios;

    @BeforeEach
    void setUp() {
        provider = new JSONFileScenarioProvider();
        scenarios = provider.readScenarios();
    }

    @Test
    void testReadScenarios_count() {
        assertEquals(EXPECTED_LIST_SIZE, scenarios.size());
    }

    @Test
    void testReadScenarios_type() {
        for(Scenario scenario: scenarios){
            assertEquals(Scenario.class, scenario.getClass());
        }
    }

    @AfterEach
    void tearDown() {
        provider = null;
        scenarios = null;
    }
}