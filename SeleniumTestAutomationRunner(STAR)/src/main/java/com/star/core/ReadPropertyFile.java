package com.star.core;

import com.star.Runner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
	Runner runner = Config.context.getBean(Runner.class);

	public Properties readProperty() throws FileNotFoundException {
		Properties prop = new Properties();
		FileInputStream ip;
		ip = new FileInputStream("E:\\Nitin\\prep\\selenium\\STAR\\config.properties");
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		} catch (Exception e) {
			e.printStackTrace();
			runner.setRunStatus(RunStatus.FAIL);
		}
		return prop;
	}
}
