package executor.service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StepTest {

    private static final String DEFAULT_ACTION = "Default action";
    private static final String DEFAULT_VALUE = "Default value";
    private static final String ANOTHER_ACTION = "Another action";
    private static final String ANOTHER_VALUE = "Another value";

    @Test
    public void testNoArgsConstructor() {
        Step step = new Step();

        assertNotNull(step);
    }

    @Test
    public void testAllArgsConstructor() {
        Step step = new Step(DEFAULT_ACTION, DEFAULT_VALUE);

        assertNotNull(step);
    }

    @Test
    public void testSetters() {
        Step step = new Step();
        step.setAction(DEFAULT_ACTION);
        step.setValue(DEFAULT_VALUE);

        assertNotNull(step.getAction());
        assertNotNull(step.getValue());
    }

    @Test
    public void testSameObjectsEquals() {
        Step step = new Step(DEFAULT_ACTION, DEFAULT_VALUE);

        assertEquals(step, new Step(DEFAULT_ACTION, DEFAULT_VALUE));
    }

    @Test
    public void testDifferentObjectsNotEquals() {
        Step step = new Step(DEFAULT_ACTION, DEFAULT_VALUE);

        assertNotEquals(step, new Step(ANOTHER_ACTION, ANOTHER_VALUE));
    }

    @Test
    public void testSameObjectsHashCode() {
        Step step = new Step(DEFAULT_ACTION, DEFAULT_VALUE);

        assertEquals(step.hashCode(), new Step(DEFAULT_ACTION, DEFAULT_VALUE).hashCode());
    }

    @Test
    public void testDifferentObjectsHashCode() {
        Step step = new Step(DEFAULT_ACTION, DEFAULT_VALUE);

        assertNotEquals(step.hashCode(), new Step(ANOTHER_ACTION, ANOTHER_VALUE).hashCode());
    }
}
