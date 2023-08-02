package executor.service.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class StepTest {
    public static final String DEFAULT_ACTION = "Default action";
    public static final String DEFAULT_VALUE = "Default value";
    public static final String ANOTHER_ACTION = "Another action";
    public static final String ANOTHER_VALUE = "Another value";

    @Test
    public void testNoArgsConstructor() {
        assertNotNull(new Step());
    }

    @Test
    public void testAllArgsConstructor() {
        assertNotNull(new Step(DEFAULT_ACTION, DEFAULT_VALUE));
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
        assertTrue(step.equals(new Step(DEFAULT_ACTION, DEFAULT_VALUE)));
    }

    @Test
    public void testDifferentObjectsNotEquals() {
        Step step = new Step(DEFAULT_ACTION, DEFAULT_VALUE);
        assertFalse(step.equals(new Step(ANOTHER_ACTION, ANOTHER_VALUE)));
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
