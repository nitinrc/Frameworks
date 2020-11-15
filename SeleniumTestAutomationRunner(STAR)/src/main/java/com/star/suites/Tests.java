package com.star.suites;

import com.star.Runner;
import com.star.core.Config;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class Tests {
    Runner runner = Config.context.getBean(Runner.class);

    public void runTests() {
        TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
        TestNG testNG = new TestNG();
        if (runner.getTestSuite().toUpperCase().equals("REGRESSION")) {
            testNG.setTestClasses(new Class[] {Regression.class});
        } else if (runner.getTestSuite().toUpperCase().equals("SMOKE")) {
            testNG.setTestClasses(new Class[] {Smoke.class});
        } else if (runner.getTestSuite().toUpperCase().equals("CUSTOM")) {
            testNG.setTestClasses(new Class[] {Custom.class});
        }
        testNG.addListener(testListenerAdapter);
        testNG.run();
    }
}
