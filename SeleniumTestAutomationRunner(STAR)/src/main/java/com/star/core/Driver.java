package com.star.core;

import com.star.Runner;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class Driver {
    Runner runner = Config.context.getBean(Runner.class);
    DataFetch dataFetch = Config.context.getBean(DataFetch.class);

    public void testsRunner(String coverage) {
        log.info("Running Test Suite: {}", coverage);
        boolean flagCoverage;
        String TCID;
        int intDataIterations;

        //Iterate Rows
        Iterator hmIterator1 = dataFetch.getMapTestCases().entrySet().iterator();
        while (hmIterator1.hasNext()) {
            Map.Entry mapElement1 = (Map.Entry)hmIterator1.next();
            TCID = (String) mapElement1.getKey();
            //Iterates Test Data iterations
            intDataIterations = dataFetch.getMapData().get(TCID).size();
            for (int itrData = 1; itrData <= intDataIterations; itrData++) {
                runner.setRunStatus(RunStatus.NOT_STARTED);
                HashMap<String, String> mapTCData = dataFetch.getMapTestCases().get(mapElement1.getKey());
                //log.info("mapTCData: {}", mapTCData);
                flagCoverage = false;
                for (String itemCoverage : mapTCData.get("Coverage").split(":")) {
                    if (itemCoverage.toLowerCase().equals(coverage.toLowerCase())) {
                        flagCoverage = true;
                        break;
                    }
                }
                //Iterate Columns
                Iterator hmIterator2 = mapTCData.entrySet().iterator();
                while (hmIterator2.hasNext()) {
                    Map.Entry mapElement2 = (Map.Entry)hmIterator2.next();
                    String val = (String) mapElement2.getValue();
                    //log.info("{} : {}", mapElement2.getKey(), val);
                    if ((!(mapElement2.getKey().equals("Coverage"))) & flagCoverage) {
                        try {
                            Class<?> refClass = Class.forName("com.star.pages." + val.split("\\.")[0]);
                            Method method = null;
                            try {
                                method = refClass.newInstance().getClass().getMethod(val.split("\\.")[1], String.class, int.class);
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (SecurityException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (Exception e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            }
                            try {
                                method.invoke(refClass.newInstance(), TCID, itrData);
                                if (runner.getRunStatus().equals(RunStatus.FAIL)) {
                                    break;
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            } catch (Exception e) {
                                e.printStackTrace();
                                runner.setRunStatus(RunStatus.FAIL);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            runner.setRunStatus(RunStatus.FAIL);
                        } catch (Exception e) {
                            e.printStackTrace();
                            runner.setRunStatus(RunStatus.FAIL);
                        }
                    }
                }
            }
        }
    }
}
