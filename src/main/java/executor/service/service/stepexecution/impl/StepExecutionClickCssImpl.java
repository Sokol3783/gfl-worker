package executor.service.service.stepexecution.impl;

import executor.service.model.Step;
import executor.service.model.StepTypes;
import executor.service.service.stepexecution.StepExecutionClickCss;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickCssImpl implements StepExecutionClickCss {

  @Override
  public String getStepAction() {
    return StepTypes.CLICK_CSS.getName();
  }

  @Override
  public void step(WebDriver webDriver, Step step) {
    try {
      WebElement element = webDriver.findElement(By.cssSelector(step.getValue()));
      element.click();
    } catch (NoSuchElementException e ) {
      System.out.println("this is error");
    }

  }
}
