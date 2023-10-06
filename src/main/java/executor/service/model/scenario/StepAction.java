package executor.service.model.scenario;

public enum StepAction {
  CLICK_CSS("clickCss"),
  SLEEP("sleep"),
  CLICK_XPATH("clickXpath");

  private final String name;

  StepAction(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
