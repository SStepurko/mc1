package com.example.mc1.service;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class ConfigReader {

	//	app config file properties
	private final String runtimeKey = "runtime";
	private final String periodKey = "period";
	private final String fileName = "app.config";
	private final int runtimeKeyDefault = 1;
	private final int periodKeyDefault = 1;
	Properties properties = new Properties();

	public Map<String, Integer> getProperties() {

		try (FileInputStream fis = new FileInputStream(fileName)) {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, Integer> map = new HashMap<>();
//		check runtime and try to convert it to int
		int runTimeValue;
		try {
			runTimeValue = Integer.parseInt(properties.getProperty(runtimeKey));
		} catch (NumberFormatException e) {
			runTimeValue = runtimeKeyDefault;
			e.printStackTrace();
		}
//		check period and try to convert it to int
		int periodValue;
		try {
			periodValue = Integer.parseInt(properties.getProperty(periodKey));
		} catch (NumberFormatException e) {
			periodValue = periodKeyDefault;
			e.printStackTrace();
		}
		map.put(runtimeKey, runTimeValue);
		map.put(periodKey, periodValue);
		return map;
	}

	public String getRuntimeKey() {
		return runtimeKey;
	}

	public String getPeriodKey() {
		return periodKey;
	}
}
