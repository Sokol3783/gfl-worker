package executor.service.model.scenario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class StepTest {

    private static final String DEFAULT_ACTION = StepAction.SLEEP.getName();
    private static final String DEFAULT_VALUE = "Default value";
    private static final String ANOTHER_ACTION = StepAction.SLEEP.getName();;
    private static final String ANOTHER_VALUE = "Another value";

    @Test
     void testNoArgsConstructor() {
        Step step = new Step();

        assertNotNull(step);
    }

    @Test
     void testAllArgsConstructor() {
        Step step = prepareStep();

        assertNotNull(step);
    }

    @Test
     void testSetters() {
        Step step = new Step();

        step.setAction(StepAction.valueOf(DEFAULT_ACTION));
        step.setValue(DEFAULT_VALUE);


        assertNotNull(step.getAction());
        assertNotNull(step.getValue());
    }

    @Test
     void testSameObjectsEquals() {
        Step step = prepareStep();
        Step sameStep = prepareStep();

        assertEquals(step, sameStep);
    }

    @Test
     void testDifferentObjectsNotEquals() {
        Step step = prepareStep();
        Step anotherStep = prepareAnotherStep();

        assertNotEquals(step, anotherStep);
    }

    @Test
     void testSameObjectsHashCode() {
        Step step = prepareStep();
        Step sameStep = prepareStep();

        assertEquals(step.hashCode(), sameStep.hashCode());
    }

    @Test
     void testDifferentObjectsHashCode() {
        Step step = prepareStep();
        Step anotherStep = prepareAnotherStep();

        assertNotEquals(step.hashCode(), anotherStep.hashCode());
    }

    private Step prepareStep() {
        return new Step(StepAction.valueOf(DEFAULT_ACTION), DEFAULT_VALUE);
    }

    private Step prepareAnotherStep() {
        return new Step(StepAction.valueOf(ANOTHER_ACTION), ANOTHER_VALUE);
    }
}