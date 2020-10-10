package com.star;

import com.star.core.*;
import com.star.suites.Tests;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class Runner {
    private String testSuite;
    private RunStatus runStatus;

    public Runner(String testSuite, RunStatus runStatus) {
        this.testSuite = testSuite;
        this.runStatus = runStatus;
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(BeanContextRegister.class);
        Runner runner = Config.context.getBean(Runner.class, args[0], RunStatus.NOT_STARTED);
        log.info("Test Suite: {}", runner.getTestSuite());
        log.info("Browser: {}", args[1]);
        runner.instantiateBrowserConfig(args[1]);
        
        Tests tests = Config.context.getBean(Tests.class);
        tests.runTests();
    }

    public void instantiateBrowserConfig(String browser) {
        Config.context.getBean(BrowserConfig.class, browser);
    }
}
