package executor.service;

import executor.service.DIFactory.ObjectFactoryImpl;
import executor.service.service.StepExecutionClickCss;

public class App {

  public static void main(String[] args)
      throws Throwable {

    ObjectFactoryImpl factory = ObjectFactoryImpl.getInstance();
    StepExecutionClickCss stepExecutionClickCss1 = factory.create(StepExecutionClickCss.class);
    StepExecutionClickCss stepExecutionClickCss2 = factory.create(StepExecutionClickCss.class);
    System.out.println(stepExecutionClickCss1.getStepAction());
    System.out.println(stepExecutionClickCss2.getStepAction());
    System.out.println(stepExecutionClickCss1.equals(stepExecutionClickCss2));
  }
}
