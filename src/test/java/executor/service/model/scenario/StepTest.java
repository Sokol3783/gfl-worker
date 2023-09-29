package executor.service.model.scenario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StepTest {

    private static final String DEFAULT_ACTION = StepTypes.SLEEP.getName();
    private static final String DEFAULT_VALUE = "Default value";
    private static final String ANOTHER_ACTION = StepTypes.SLEEP.getName();;
    private static final String ANOTHER_VALUE = "Another value";

    @Test
    public void testNoArgsConstructor() {
        Step step = new Step();

        assertNotNull(step);
    }

    @Test
    public void testAllArgsConstructor() {
        Step step = prepareStep();

        assertNotNull(step);
    }

    @Test
    public void testSetters() {
        Step step = new Step();

        step.setAction(StepTypes.valueOf(DEFAULT_ACTION));
        step.setValue(DEFAULT_VALUE);


        assertNotNull(step.getAction());
        assertNotNull(step.getValue());
    }

    @Test
    public void testSameObjectsEquals() {
        Step step = prepareStep();
        Step sameStep = prepareStep();

        assertEquals(step, sameStep);
    }

    @Test
    public void testDifferentObjectsNotEquals() {
        Step step = prepareStep();
        Step anotherStep = prepareAnotherStep();

        assertNotEquals(step, anotherStep);
    }

    @Test
    public void testSameObjectsHashCode() {
        Step step = prepareStep();
        Step sameStep = prepareStep();

        assertEquals(step.hashCode(), sameStep.hashCode());
    }

    @Test
    public void testDifferentObjectsHashCode() {
        Step step = prepareStep();
        Step anotherStep = prepareAnotherStep();

        assertNotEquals(step.hashCode(), anotherStep.hashCode());
    }

    private Step prepareStep() {
        return new Step(StepTypes.valueOf(DEFAULT_ACTION), DEFAULT_VALUE);
    }

    private Step prepareAnotherStep() {
        return new Step(StepTypes.valueOf(ANOTHER_ACTION), ANOTHER_VALUE);
    }
}