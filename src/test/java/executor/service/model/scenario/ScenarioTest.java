package executor.service.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ScenarioTest {

    private static final String NAME = "Test Scenario";
    private static final String NAME_ANOTHER = "Scenario 1";
    private static final String SITE = "example.com";
    private static final String SITE_ANOTHER = "site1.com";
    private List<Step> steps1;
    private List<Step> steps2;
    private List<Step> steps3;

    @BeforeEach
    public void setUp() {
        steps1 = new ArrayList<>();
        steps1.add(new Step(StepTypes.SLEEP, "5000:15000"));
        steps1.add(new Step(StepTypes.CLICK_CSS, "body > ul > li:nth-child(1) > a"));

        steps2 = new ArrayList<>();
        steps2.add(new Step(StepTypes.SLEEP, "5000:15000"));
        steps2.add(new Step(StepTypes.CLICK_CSS, "body > ul > li:nth-child(1) > a"));

        steps3 = new ArrayList<>();
        steps3.add(new Step(StepTypes.SLEEP, "5000:15000"));
        steps3.add(new Step(StepTypes.CLICK_CSS, "body"));
    }

    @AfterEach
    public void tearDown() {
        steps1 = null;
        steps2 = null;
        steps3 = null;
    }

    @Test
    public void testConstructorWithoutArgsAndSettersAndGetters() {
        Scenario scenario = new Scenario();
        scenario.setName(NAME);
        scenario.setSite(SITE);
        scenario.setSteps(steps1);

        assertEquals(NAME, scenario.getName());
        assertEquals(SITE, scenario.getSite());
        assertEquals(steps1, scenario.getSteps());
    }

    @Test
    public void testConstructorWithArgsAndGetters() {
        Scenario scenario = new Scenario(NAME, SITE, steps1);

        assertEquals(NAME, scenario.getName());
        assertEquals(SITE, scenario.getSite());
        assertEquals(steps1, scenario.getSteps());
    }

    @Test
    public void testEquals() {
        Scenario scenario1 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps1);
        Scenario scenario2 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps2);

        assertEquals(scenario1, scenario2);
    }

    @Test
    public void testNotEquals() {
        Scenario scenario1 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps1);
        Scenario scenario2 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps3);

        assertNotEquals(scenario1, scenario2);
    }

    @Test
    public void testHashCode() {
        Scenario scenario1 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps1);
        Scenario scenario2 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps2);

        assertEquals(scenario1.hashCode(), scenario2.hashCode());
    }

    @Test
    public void testHashCodeNotMatch() {
        Scenario scenario1 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps1);
        Scenario scenario2 = new Scenario(NAME_ANOTHER, SITE_ANOTHER, steps3);

        assertNotEquals(scenario1.hashCode(), scenario2.hashCode());
    }
}
