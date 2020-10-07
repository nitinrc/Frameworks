package com.star;

import com.star.core.*;
import com.star.suites.Regression;
import com.star.suites.Smoke;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

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

    public static void main(String[] arg) {
        String[] args = new String[3];
        args[0] = "Regression";
        args[1] = "Chrome";
        new AnnotationConfigApplicationContext(BeanContextRegister.class);
        Runner runner = Config.context.getBean(Runner.class, args[0], RunStatus.NOT_STARTED);
        log.info("Test Suite: {}", runner.getTestSuite());
        log.info("Browser: {}", args[1]);
        runner.instantiateBrowserConfig(args[1]);

        TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
        TestNG testNG = new TestNG();
        if (runner.getTestSuite().equals("Regression")) {
            testNG.setTestClasses(new Class[] {Regression.class});
        } else if (runner.getTestSuite().equals("Smoke")) {
            testNG.setTestClasses(new Class[] {Smoke.class});
        }
        testNG.addListener(testListenerAdapter);
        testNG.run();
    }

    public void instantiateBrowserConfig(String browser) {
        Config.context.getBean(BrowserConfig.class, browser);
    }
}
