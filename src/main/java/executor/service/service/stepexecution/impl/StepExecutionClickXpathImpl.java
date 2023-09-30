package executor.service.service.stepexecution.impl;

import executor.service.model.scenario.Step;
import executor.service.model.scenario.StepTypes;
import executor.service.service.stepexecution.StepExecutionClickXpath;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


//TODO дописать логирование
public class StepExecutionClickXpathImpl implements StepExecutionClickXpath {

    @Override
    public String getNameStepAction() {
        return StepTypes.CLICK_XPATH.getName();
    }

    @Override
    public StepTypes getStepAction() {
        return StepTypes.CLICK_XPATH;
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
