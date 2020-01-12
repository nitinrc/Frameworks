package selenium.framework;

import tests.Runner;

public class App {
	public static String testSuite;
	public static String browser;
	public static void main(String[] args) {
		App.testSuite = args[0];
		App.browser = args[1];
		System.out.println("Test Suite: "+App.testSuite);
		System.out.println("Browser: "+App.browser);
		String[] args1 = {};
		Runner.main(args1);
	}
}