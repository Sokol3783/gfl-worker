package executor.service.model.scenario;

import java.util.Objects;

public class Step {
    private StepAction action;
    private String value;

    public Step() {
    }

    public Step(StepAction action, String value) {
        this.action = action;
        this.value = value;
    }

    public StepAction getAction() {
        return action;
    }

    public void setAction(StepAction action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return action.equals(step.action) && value.equals(step.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, value);
    }

    @Override
    public String toString() {
        return "Step{" +
            "action='" + action + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
