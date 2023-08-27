package executor.service.service;

import executor.service.service.impl.*;

public class FactoryForDI {
    private final WebDriverInitializer webDriverInitializer;
    private final ProxySourcesClient proxySourcesClient;
    //private final ScenarioSourceListener scenarioSourceListener;
    //private final ParalleFlowExecutorService paralleFlowExecutorService;
    //private final ExecutionService executionService;
    private final ScenarioExecutor scenarioExecutor;
    private final StepExecutionSleep stepExecutionSleep;
    private final StepExecutionClickXpath stepExecutionClickXpath;

    private final StepExecutionClickCss stepExecutionClickCss;

    public FactoryForDI() {
        webDriverInitializer = new WebDriverInitializer(null,null);
        proxySourcesClient = new ProxySourcesClientImpl();
        //scenarioSourceListener = new ScenarioSourceListenerImpl(step);
        //paralleFlowExecutorService = new ParalleFlowExecutorService(null,null);
        //executionService = new ExecutionServiceImpl();
        scenarioExecutor = new ScenarioExecutorImpl();
        stepExecutionSleep = new StepExecutionSleepImpl();
        stepExecutionClickCss = new StepExecutionClickCssImpl();
        stepExecutionClickXpath = new StepExecutionClickXpathImpl();
    }

    public WebDriverInitializer getWebDriverInitializer() {
        return webDriverInitializer;
    }

    public ProxySourcesClient getProxySourcesClient() {
        return proxySourcesClient;
    }

//    public ScenarioSourceListener getScenarioSourceListener() {
//        return scenarioSourceListener;
//    }

//    public ParalleFlowExecutorService getParalleFlowExecutorService() {
//        return paralleFlowExecutorService;
//    }

//    public ExecutionService getExecutionService() {
//        return executionService;
//    }

    public ScenarioExecutor getScenarioExecutor() {
        return scenarioExecutor;
    }

    public StepExecutionSleep getStepExecutionSleep() {
        return stepExecutionSleep;
    }

    public StepExecutionClickXpath getStepExecutionClickXpath() {
        return stepExecutionClickXpath;
    }

    public StepExecutionClickCss getStepExecutionClickCss() {
        return stepExecutionClickCss;
    }

}
