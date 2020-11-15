package com.star.core;

import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
	ResultStatus resultStatus = Config.context.getBean(ResultStatus.class);

	public Properties readProperty() throws FileNotFoundException {
		Properties prop = new Properties();
		FileInputStream ip;
		ip = new FileInputStream("E:\\Nitin\\prep\\selenium\\STAR\\config.properties");
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.READ_PROPERTY_FILE_ERROR);
			Assert.assertTrue(false);
		} catch (Exception e) {
			e.printStackTrace();
			resultStatus.setRunStatus(RunStatus.FAIL);
			resultStatus.setFailureReason(FailureReasons.READ_PROPERTY_FILE_ERROR);
			Assert.assertTrue(false);
		}
		return prop;
	}
}
