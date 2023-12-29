package executor.service.service.stepexecution.impl;

import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepAction;
import executor.service.service.stepexecution.StepExecutionClickXpath;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

    @Override
    public String getNameStepAction() {
        return StepAction.CLICK_XPATH.getName();
    }

    @Override
    public StepAction getStepAction() {
        return StepAction.CLICK_XPATH;
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        try {
            WebElement element = webDriver.findElement(By.xpath(step.getValue()));
            element.click();
        } catch (NoSuchElementException e ) {
            System.out.println("this is error" + e.getMessage());
        }
    }
}
