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

    public Runner(String testSuite) {
        this.testSuite = testSuite;
    }

    public static void main(String[] args1) {
        String[] args = {"Custom", "Chrome"};
//        args[0] = "Custom";
//        args[1] = "Chrome";
        new AnnotationConfigApplicationContext(BeanContextRegister.class);
        Runner runner = Config.context.getBean(Runner.class, args[0]);
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
