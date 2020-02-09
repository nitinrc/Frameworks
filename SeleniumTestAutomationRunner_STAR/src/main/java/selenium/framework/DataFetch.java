package selenium.framework;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import springBeans.FlightBookingConfig;

public class DataFetch {//extends ReadPropertyFile {
	private TreeMap<String, HashMap<String, String>> mapTestCases;
	private HashMap<String, HashMap<Integer, HashMap<String, String>>> mapSteps;
	private HashMap<String, HashMap<String, HashMap<String, String>>> mapPOM;
	private HashMap<String, HashMap<Integer, HashMap<String, String>>> mapData;
	
	public TreeMap<String, HashMap<String, String>> getTestCases() {
		return this.mapTestCases;
	}
	public void setTestCases() {
		TreeMap<String, HashMap<String, String>> mapTCData = new TreeMap<String, HashMap<String, String>>();
		ReadPropertyFile objReadPropertyFile = FlightBookingConfig.context.getBean(ReadPropertyFile.class);
		try
	    {
			String path = objReadPropertyFile.readProperty().getProperty("ExcelPath");
	    	FileInputStream file = new FileInputStream(new File(path));
			//Create Workbook instance holding reference to .xlsx file
		    XSSFWorkbook workbook = new XSSFWorkbook(file);
		    //Get first/desired sheet from the workbook
		    XSSFSheet sheet = workbook.getSheetAt(0);
		    //Iterate through each rows one by one
		    Iterator<Row> rowIterator = sheet.iterator();
		    int intCols = 0;
		    while (rowIterator.hasNext())
		    {
		    	Row row = rowIterator.next();
		    	Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		        	cellIterator.next();
		        	intCols++;
		        }
		        break;
		    }
		    int indexRow, indexCol;
		    indexRow = 0;
		    String[] arrColHeaders = new String [intCols];
		    String keyTCID = "";
		    rowIterator = sheet.iterator();
		    while (rowIterator.hasNext())
		    {
		    	HashMap<String, String> mapData = new HashMap<String, String>();
		    	indexCol = 0;
		    	indexRow++;
		    	Row row = rowIterator.next();
		        //For each row, iterate through all the columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		            Cell cell = cellIterator.next();
		            //Check the cell type and format accordingly
		            switch (cell.getCellType()) 
		            {
		                case Cell.CELL_TYPE_NUMERIC:
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    if (indexCol == 0) {
		                    	try {
		                    		keyTCID = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		                    }
		                    if (indexRow == 1) {
		                    	try {
		                    		arrColHeaders[indexCol] = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		    		    	} else {
		    		    		if (!(arrColHeaders[indexCol].equals("TCID"))) {
		    		    			try {
		    		    				mapData.put(arrColHeaders[indexCol], cell.getStringCellValue());
		    		    			} catch (Exception e) {e.printStackTrace();}
			    		    	}
		    		    	}
		                    break;
		            }
		            indexCol++;
		        }
		        if (indexRow > 1) {
		        	mapTCData.put(keyTCID, mapData);
			    }
		    }
		    file.close();
		} 
		catch (Exception e) {e.printStackTrace();}
		//TreeMap<String, HashMap<String, String>> sortedTCMap = new TreeMap();
		//sortedTCMap.putAll(mapTCData);
	    this.mapTestCases = mapTCData;
	    //return mapTCData;
	}
	
	public HashMap<String, HashMap<Integer, HashMap<String, String>>> getSteps() {
		return this.mapSteps;
	}
	public void setSteps() {
		HashMap<Integer, HashMap<String, String>> mapStep = new HashMap<Integer, HashMap<String, String>>();
	    HashMap<String, HashMap<Integer, HashMap<String, String>>> mapComponent = new HashMap<String, HashMap<Integer, HashMap<String, String>>>();
	    ReadPropertyFile objReadPropertyFile = FlightBookingConfig.context.getBean(ReadPropertyFile.class);
	    try
	    {
	    	String path = objReadPropertyFile.readProperty().getProperty("ExcelPath");
	    	FileInputStream file = new FileInputStream(new File(path));
			//Create Workbook instance holding reference to .xlsx file
		    XSSFWorkbook workbook = new XSSFWorkbook(file);
		    //Get first/desired sheet from the workbook
		    XSSFSheet sheet = workbook.getSheetAt(1);
		    //Iterate through each rows one by one
		    Iterator<Row> rowIterator = sheet.iterator();
		    int intCols = 0;
		    while (rowIterator.hasNext())
		    {
		    	Row row = rowIterator.next();
		    	Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		        	cellIterator.next();
		        	intCols++;
		        }
		        break;
		    }
		    int indexRow, indexCol, currentRow;
		    indexRow = 0;
		    currentRow = 0;
		    //Array arrColHeaders[] = new Array[];
		    String[] arrColHeaders = new String [intCols];
		    Integer keyStep = -1;
		    String component = "";
		    String componentName = "";
		    //mapComponent = null;
		    rowIterator = sheet.iterator();
		    while (rowIterator.hasNext())
		    {
		    	HashMap<String, String> mapData = new HashMap<String, String>();
		    	indexCol = 0;
		    	indexRow++;
		    	Row row = rowIterator.next();
		        //For each row, iterate through all the columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		            Cell cell = cellIterator.next();
		            //Check the cell type and format accordingly
		            switch (cell.getCellType()) 
		            {
		                case Cell.CELL_TYPE_NUMERIC:
		                    keyStep = (int) cell.getNumericCellValue();
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    if (indexCol == 0) {
		                    	try {
		                    		componentName = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		                    }
		                    if (indexRow == 1) {
		                    	try {
		                    		arrColHeaders[indexCol] = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		    		    	} else {
		    		    		if ((arrColHeaders[indexCol].equals("Element")) || (arrColHeaders[indexCol].equals("Action")) || (arrColHeaders[indexCol].equals("Input"))) {
		    		    			try {
		    		    				if (indexRow != currentRow) {
		    		    					mapData.clear();
		    		    					currentRow = indexRow;
		    		    				}
		    		    				mapData.put(arrColHeaders[indexCol], cell.getStringCellValue());
		    		    			} catch (Exception e) {e.printStackTrace();}
			    		    	}
		    		    	}
		                    break;
		            }
		            indexCol++;
		        }
		        if (indexRow > 1) {
		        	if (!(component.equals(componentName))) {
		    			mapStep = new HashMap<Integer, HashMap<String, String>>();
		    			component = componentName;
		        	}
		        	mapStep.put(keyStep, mapData);
			        mapComponent.put(componentName, mapStep);
			    }
		    }
		    file.close();
		} 
		catch (Exception e) {e.printStackTrace();}
	    this.mapSteps = mapComponent;
		//return mapComponent;
	}
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> getPOM() {
		return this.mapPOM;
	}
	public void setPOM() {
		HashMap<String, HashMap<String, String>> mapElement = new HashMap<String, HashMap<String, String>>();
	    HashMap<String, HashMap<String, HashMap<String, String>>> mapPage = new HashMap<String, HashMap<String, HashMap<String, String>>>();
	    ReadPropertyFile objReadPropertyFile = FlightBookingConfig.context.getBean(ReadPropertyFile.class);
	    try
	    {
	    	String path = objReadPropertyFile.readProperty().getProperty("ExcelPath");
	    	FileInputStream file = new FileInputStream(new File(path));
			//Create Workbook instance holding reference to .xlsx file
		    XSSFWorkbook workbook = new XSSFWorkbook(file);
		    //Get first/desired sheet from the workbook
		    XSSFSheet sheet = workbook.getSheetAt(2);
		    //Iterate through each rows one by one
		    Iterator<Row> rowIterator = sheet.iterator();
		    int intCols = 0;
		    while (rowIterator.hasNext())
		    {
		    	Row row = rowIterator.next();
		    	Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		        	cellIterator.next();
		        	intCols++;
		        }
		        break;
		    }
		    int indexRow, indexCol, currentRow;
		    indexRow = 0;
		    currentRow = 0;
		    String[] arrColHeaders = new String [intCols];
		    String keyElement = "";
		    String page = "";
		    String pageName = "";
		    rowIterator = sheet.iterator();
		    while (rowIterator.hasNext())
		    {
		    	HashMap<String, String> mapData = new HashMap<String, String>();
		    	indexCol = 0;
		    	indexRow++;
		    	Row row = rowIterator.next();
		        //For each row, iterate through all the columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		            Cell cell = cellIterator.next();
		            //Check the cell type and format accordingly
		            switch (cell.getCellType()) 
		            {
		                case Cell.CELL_TYPE_NUMERIC:
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    if (indexCol == 0) {
		                    	try {
		                    		pageName = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		                    }
		                    if (indexCol == 1) {
		                    	try {
		                    		keyElement = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		                    }
		                    if (indexRow == 1) {
		                    	try {
		                    		arrColHeaders[indexCol] = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		    		    	} else {
		    		    		if ((arrColHeaders[indexCol].equals("Type")) || (arrColHeaders[indexCol].equals("Locator")) || (arrColHeaders[indexCol].equals("LocatorType")) || (arrColHeaders[indexCol].equals("ExpectedCondition")) || (arrColHeaders[indexCol].equals("Timeout"))) {
		    		    			try {
		    		    				if (indexRow != currentRow) {
		    		    					mapData.clear();
		    		    					currentRow = indexRow;
		    		    				}
		    		    				mapData.put(arrColHeaders[indexCol], cell.getStringCellValue());
		    		    			} catch (Exception e) {e.printStackTrace();}
			    		    	}
		    		    	}
		                    break;
		            }
		            indexCol++;
		        }
		        if (indexRow > 1) {
		        	if (!(page.equals(pageName))) {
		    			mapElement = new HashMap<String, HashMap<String, String>>();
		    			page = pageName;
		        	}
		        	mapElement.put(keyElement, mapData);
			        mapPage.put(pageName, mapElement);
			    }
		    }
		    file.close();
		} 
		catch (Exception e) {e.printStackTrace();}
	    this.mapPOM = mapPage;
		//return mapPage;
	}
	
	public HashMap<String, HashMap<Integer, HashMap<String, String>>> getData() {
		return this.mapData;
	}
	public void setData() {
		HashMap<Integer, HashMap<String, String>> mapStep = new HashMap<Integer, HashMap<String, String>>();
	    HashMap<String, HashMap<Integer, HashMap<String, String>>> mapTestData = new HashMap<String, HashMap<Integer, HashMap<String, String>>>();
	    ReadPropertyFile objReadPropertyFile = FlightBookingConfig.context.getBean(ReadPropertyFile.class);
	    try
	    {
	    	String path = objReadPropertyFile.readProperty().getProperty("ExcelPath");
	    	FileInputStream file = new FileInputStream(new File(path));
			//Create Workbook instance holding reference to .xlsx file
		    XSSFWorkbook workbook = new XSSFWorkbook(file);
		    //Get first/desired sheet from the workbook
		    XSSFSheet sheet = workbook.getSheetAt(3);
		    //Iterate through each rows one by one
		    Iterator<Row> rowIterator = sheet.iterator();
		    int intCols = 0;
		    while (rowIterator.hasNext())
		    {
		    	Row row = rowIterator.next();
		    	Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		        	cellIterator.next();
		        	intCols++;
		        }
		        break;
		    }
		    int indexRow, indexCol, currentRow;
		    indexRow = 0;
		    currentRow = 0;
		    String[] arrColHeaders = new String [intCols];
		    Integer keyStep = -1;
		    String TCID = "";
		    String TCIDName = "";
		    //mapComponent = null;
		    rowIterator = sheet.iterator();
		    while (rowIterator.hasNext())
		    {
		    	HashMap<String, String> mapData = new HashMap<String, String>();
		    	indexCol = 0;
		    	indexRow++;
		    	Row row = rowIterator.next();
		        //For each row, iterate through all the columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) 
		        {
		            Cell cell = cellIterator.next();
		            //Check the cell type and format accordingly
		            switch (cell.getCellType()) 
		            {
		                case Cell.CELL_TYPE_NUMERIC:
		                    keyStep = (int) cell.getNumericCellValue();
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    if (indexCol == 0) {
		                    	try {
		                    		TCIDName = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		                    }
		                    if (indexRow == 1) {
		                    	try {
		                    		arrColHeaders[indexCol] = cell.getStringCellValue();
		                    	} catch (Exception e) {e.printStackTrace();}
		    		    	} else {
		    		    		if ((!(arrColHeaders[indexCol].equals("TCID"))) & ((!arrColHeaders[indexCol].equals("Iteration")))) {
		    		    			try {
		    		    				if (indexRow != currentRow) {
		    		    					mapData.clear();
		    		    					currentRow = indexRow;
		    		    				}
		    		    				mapData.put(arrColHeaders[indexCol], cell.getStringCellValue());
		    		    			} catch (Exception e) {e.printStackTrace();}
			    		    	}
		    		    	}
		                    break;
		            }
		            indexCol++;
		        }
		        if (indexRow > 1) {
		        	if (!(TCID.equals(TCIDName))) {
		    			mapStep = new HashMap<Integer, HashMap<String, String>>();
		    			TCID = TCIDName;
		        	}
		        	mapStep.put(keyStep, mapData);
		        	mapTestData.put(TCIDName, mapStep);
			    }
		    }
		    file.close();
		} 
		catch (Exception e) {e.printStackTrace();}
	    this.mapData = mapTestData;
		//return mapTestData;
	}
}
