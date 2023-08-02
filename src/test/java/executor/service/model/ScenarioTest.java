package executor.service.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScenarioTest {

    private List<Step> steps1;
    private List<Step> steps2;
    private List<Step> steps3;

    @Before
    public void setUp() {
        steps1 = new ArrayList<>();
        steps1.add(new Step("sleep", "5000:15000"));
        steps1.add(new Step("clickcss", "body > ul > li:nth-child(1) > a"));

        steps2 = new ArrayList<>();
        steps2.add(new Step("sleep", "5000:15000"));
        steps2.add(new Step("clickcss", "body > ul > li:nth-child(1) > a"));

        steps3 = new ArrayList<>();
        steps3.add(new Step("sleep", "5000:15000"));
        steps3.add(new Step("click", "body"));
    }

    @After
    public void tearDown() {
        steps1 = null;
        steps2 = null;
        steps3 = null;
    }

    @Test
    public void testConstructorAndSettersAndGetters() {
        Scenario scenario = new Scenario();
        scenario.setName("Test Scenario");
        scenario.setSite("example.com");
        scenario.setSteps(steps1);

        assertEquals("Test Scenario", scenario.getName());
        assertEquals("example.com", scenario.getSite());
        assertEquals(steps1, scenario.getSteps());
    }

    @Test
    public void testConstructorAndGetters() {
        Scenario scenario = new Scenario("Test Scenario", "example.com", steps1);

        assertEquals("Test Scenario", scenario.getName());
        assertEquals("example.com", scenario.getSite());
        assertEquals(steps1, scenario.getSteps());
    }

    @Test
    public void testEquals() {
        Scenario scenario1 = new Scenario("Scenario 1", "site1.com", steps1);
        Scenario scenario2 = new Scenario("Scenario 1", "site1.com", steps2);

        assertEquals(scenario1, scenario2);
    }

    @Test
    public void testHashCode() {
        Scenario scenario1 = new Scenario("Scenario 1", "site1.com", steps1);
        Scenario scenario2 = new Scenario("Scenario 1", "site1.com", steps2);

        assertEquals(scenario1.hashCode(), scenario2.hashCode());
    }

    @Test
    public void testNotEquals() {
        Scenario scenario1 = new Scenario("Scenario 1", "site1.com", steps1);
        Scenario scenario2 = new Scenario("Scenario 1", "site1.com", steps3);

        assertNotEquals(scenario1, scenario2);
        assertNotEquals(scenario1.hashCode(), scenario2.hashCode());
    }
}