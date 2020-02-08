package selenium.framework;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.openqa.selenium.WebElement;

import springBeans.FlightBookingConfig;

public class MultiThreadingExecutorService {
	public void dataFetch() throws InterruptedException, ExecutionException {
		System.out.println("Executor Service for data fetch");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		callables.add(new Callable<String>(){
			public String call() throws Exception {
				DataFetch objData = FlightBookingConfig.context.getBean(DataFetch.class);
				objData.setTestCases();
				return "Thread: Fetched Test Cases";
			}
		});
		callables.add(new Callable<String>(){
			public String call() throws Exception {
				DataFetch objData = FlightBookingConfig.context.getBean(DataFetch.class);
				objData.setSteps();
				return "Thread: Fetched Steps";
			}
		});
		callables.add(new Callable<String>(){
			public String call() throws Exception {
				DataFetch objData = FlightBookingConfig.context.getBean(DataFetch.class);
				objData.setPOM();
				return "Thread: Fetched POM";
			}
		});
		callables.add(new Callable<String>(){
			public String call() throws Exception {
				DataFetch objData = FlightBookingConfig.context.getBean(DataFetch.class);
				objData.setData();
				return "Thread: Fetched Data";
			}
		});
		
		List<Future<String>> futures = es.invokeAll(callables);
		for (Future<String> future : futures) {
			System.out.println("future.get() at time: "+timeOnly.format(cal.getTime()));
			System.out.println("future.get(): "+future.get());
		}
		es.shutdown();
		System.out.println("es.isShutdown(): "+es.isShutdown());
	}
	
	public HashMap<String, HashMap<String, String>> getElementIdentificationData(String[] arrFields, final String component) throws InterruptedException, ExecutionException {
		System.out.println("Executor Service for getting element identification data for verification");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Set<Callable<HashMap<String, String>>> callables = new HashSet<Callable<HashMap<String, String>>>();
		for (final String item : arrFields) {
		    System.out.println("Getting parameters for element: "+item);
		    callables.add(new Callable<HashMap<String, String>>(){
				public HashMap<String, String> call() throws Exception {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
					DataFetch objDataFetch = FlightBookingConfig.context.getBean(DataFetch.class);
					HashMap<String, String> mapElementParameters = new HashMap<String, String>();
					mapElementParameters.put("Locator", objDataFetch.getPOM().get(component).get(item).get("Locator"));
					mapElementParameters.put("LocatorType", objDataFetch.getPOM().get(component).get(item).get("LocatorType"));
					mapElementParameters.put("ExpectedCondition", objDataFetch.getPOM().get(component).get(item).get("ExpectedCondition"));
					mapElementParameters.put("Timeout", objDataFetch.getPOM().get(component).get(item).get("Timeout"));
					System.out.println("Getting parameters for element: "+item+" at time: "+timeOnly.format(cal.getTime()));
					return mapElementParameters;
				}
		    });
		}
		
		int intCounter = 0;
		HashMap<String, HashMap<String, String>> mapElements = new HashMap<String, HashMap<String, String>>();
		List<Future<HashMap<String, String>>> futures = es.invokeAll(callables);
		for (Future<HashMap<String, String>> future : futures) {
			System.out.println("future.get() at time: "+timeOnly.format(cal.getTime()));
			HashMap<String, String> mapElementParameters = future.get();
			System.out.println("mapElements.put | key: "+arrFields[intCounter]+" | value: "+mapElementParameters);
			mapElements.put(arrFields[intCounter], mapElementParameters);
			intCounter++;
		}
		es.shutdown();
		System.out.println("es.isShutdown(): "+es.isShutdown());
		return mapElements;
	}
	
	public void verifyElements(HashMap<String, HashMap<String, String>> mapElements) throws InterruptedException, ExecutionException {
		System.out.println("Executor Service for verifying elements");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		for (final Entry<String, HashMap<String, String>> item : mapElements.entrySet()) {
		    System.out.println("Element | key: "+item.getKey()+" | value:"+item.getValue());
		    callables.add(new Callable<String>(){
				public String call() throws Exception {
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
					FindElement objFindElement = FlightBookingConfig.context.getBean(FindElement.class);
					HashMap<String, String> mapElementParameters = item.getValue();
					WebElement element = objFindElement.findElement(mapElementParameters.get("Locator"), mapElementParameters.get("LocatorType"), mapElementParameters.get("ExpectedCondition"), mapElementParameters.get("Timeout"));
					System.out.println("Element exist check at time: "+timeOnly.format(cal.getTime()));
					if (element == null) {
						return item.getKey() + " does NOT exist";
					} else {
						return item.getKey() + " exists";
					}
				}
			});
		}
		
		List<Future<String>> futures = es.invokeAll(callables);
		for (Future<String> future : futures) {
			System.out.println("future.get() at time: "+timeOnly.format(cal.getTime()));
			System.out.println("future.get(): "+future.get());
		}
		es.shutdown();
		System.out.println("es.isShutdown(): "+es.isShutdown());
	}
}

