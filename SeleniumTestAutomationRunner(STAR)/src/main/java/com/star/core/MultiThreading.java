package com.star.core;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

@Slf4j
public class MultiThreading {
	public void dataFetch() throws InterruptedException, ExecutionException {
		log.info("Executor Service for data fetch");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		callables.add(() -> {
			DataFetch dataFetch = Config.context.getBean(DataFetch.class);
			dataFetch.setTestCases();
			return "Thread: Fetched Test Cases";
		});
		callables.add(() -> {
			DataFetch dataFetch = Config.context.getBean(DataFetch.class);
			dataFetch.setSteps();
			return "Thread: Fetched Steps";
		});
		callables.add(() -> {
			DataFetch dataFetch = Config.context.getBean(DataFetch.class);
			dataFetch.setPOM();
			return "Thread: Fetched POM";
		});
		callables.add(() -> {
			DataFetch dataFetch = Config.context.getBean(DataFetch.class);
			dataFetch.setData();
			return "Thread: Fetched Data";
		});
		
		List<Future<String>> futures = es.invokeAll(callables);
		for (Future<String> future : futures) {
			log.info("future.get() at time: {}", timeOnly.format(cal.getTime()));
			log.info("future.get(): {}", future.get());
		}
		es.shutdown();
		log.info("es.isShutdown(): {}", es.isShutdown());
	}
	
	public HashMap<String, HashMap<String, String>> getElementIdentificationData(String[] arrFields, final String component) throws InterruptedException, ExecutionException {
		log.info("Executor Service for getting element identification data for verification");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Set<Callable<HashMap<String, String>>> callables = new HashSet<Callable<HashMap<String, String>>>();
		for (final String item : arrFields) {
			log.info("Getting parameters for element: {}", item);
		    callables.add(() -> {
				Calendar cal1 = Calendar.getInstance();
				SimpleDateFormat timeOnly1 = new SimpleDateFormat("HH:mm:ss");
				DataFetch dataFetch = Config.context.getBean(DataFetch.class);
				HashMap<String, String> mapElementParameters = new HashMap<>();
				mapElementParameters.put("Locator", dataFetch.getMapPOM().get(component).get(item).get("Locator"));
				mapElementParameters.put("LocatorType", dataFetch.getMapPOM().get(component).get(item).get("LocatorType"));
				mapElementParameters.put("ExpectedCondition", dataFetch.getMapPOM().get(component).get(item).get("ExpectedCondition"));
				mapElementParameters.put("Timeout", dataFetch.getMapPOM().get(component).get(item).get("Timeout"));
				log.info("Getting parameters for element: {} at time: {}", item, timeOnly1.format(cal1.getTime()));
				return mapElementParameters;
			});
		}
		
		int intCounter = 0;
		HashMap<String, HashMap<String, String>> mapElements = new HashMap<String, HashMap<String, String>>();
		List<Future<HashMap<String, String>>> futures = es.invokeAll(callables);
		for (Future<HashMap<String, String>> future : futures) {
			log.info("future.get() at time: {}", timeOnly.format(cal.getTime()));
			HashMap<String, String> mapElementParameters = future.get();
			log.info("mapElements.put | key: {} | value: {}", arrFields[intCounter], mapElementParameters);
			mapElements.put(arrFields[intCounter], mapElementParameters);
			intCounter++;
		}
		es.shutdown();
		log.info("es.isShutdown(): {}", es.isShutdown());
		return mapElements;
	}
	
	public void verifyElements(HashMap<String, HashMap<String, String>> mapElements) throws InterruptedException, ExecutionException {
		log.info("Executor Service for verifying elements");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		for (final Entry<String, HashMap<String, String>> item : mapElements.entrySet()) {
			log.info("Element | key: {} | value: {}", item.getKey(), item.getValue());
		    callables.add(() -> {
				Calendar cal1 = Calendar.getInstance();
				SimpleDateFormat timeOnly1 = new SimpleDateFormat("HH:mm:ss");
				FindElement findElement = Config.context.getBean(FindElement.class);
				HashMap<String, String> mapElementParameters = item.getValue();
				WebElement element = findElement.findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
				log.info("Element exist check at time: {}", timeOnly1.format(cal1.getTime()));
				if (element == null) {
					return item.getKey() + " does NOT exist";
				} else {
					return item.getKey() + " exists";
				}
			});
		}
		
		List<Future<String>> futures = es.invokeAll(callables);
		for (Future<String> future : futures) {
			log.info("future.get() at time: {}", timeOnly.format(cal.getTime()));
			log.info("future.get(): {}", future.get());
		}
		es.shutdown();
		log.info("es.isShutdown(): {}", es.isShutdown());
	}
}
