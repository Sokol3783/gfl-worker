package executor.service.model;

public enum StepTypes {
  CLICK_CSS("clickCss"),
  SLEEP("sleep"),
  CLICK_XPATH("clickXpath");

  private final String name;

  StepTypes(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
