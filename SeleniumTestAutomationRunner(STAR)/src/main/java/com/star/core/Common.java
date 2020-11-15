package com.star.core;

import java.util.HashMap;

public class Common {
    DataFetch dataFetch = Config.context.getBean(DataFetch.class);

    public HashMap<String, String> getDataForNagivate(String TCID, int itrData) {
        HashMap<String, String> mapElementParameters = new HashMap<String, String>();
        mapElementParameters.put("Input", dataFetch.getMapData().get(TCID).get(itrData).get("URL"));
        return mapElementParameters;
    }

    public HashMap<String, String> getDataForIdentification(String component, String element, String findElementBy) {
        HashMap<String, String> mapElementParameters = new HashMap<String, String>();
        mapElementParameters.put("Locator", dataFetch.getMapPOM().get(component).get(element).get("Locator"));
        mapElementParameters.put("LocatorType", dataFetch.getMapPOM().get(component).get(element).get("LocatorType"));
        mapElementParameters.put("ExpectedCondition", dataFetch.getMapPOM().get(component).get(element).get("ExpectedCondition"));
        mapElementParameters.put("Timeout", dataFetch.getMapPOM().get(component).get(element).get("Timeout"));
        mapElementParameters.put("FindElementBy", findElementBy);
        return mapElementParameters;
    }

    public HashMap<String, String> getTestDataByTCIDAndIteration(String tcid, Integer iteration) {
        return dataFetch.getMapData().get(tcid).get(iteration);
    }
}
