package com.star.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
	public Properties readProperty() throws FileNotFoundException {
		Properties prop = new Properties();
		FileInputStream ip;
		ip = new FileInputStream("E:\\Nitin\\prep\\selenium\\STAR\\config.properties");
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
