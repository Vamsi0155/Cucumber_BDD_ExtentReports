package com.Orange.Factory;

import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loader {

    private static final Logger logger = LogManager.getLogger(Loader.class);

    private static final Map<String, String> globalValues = new HashMap<>();


    public static Map<String, String> getGlobalValues() {
        return globalValues;
    }

    public static void setGlobalValue(String key, String value) {
        globalValues.put(key, value);
    }

    public static void clearGlobalValues() {
        globalValues.clear();
    }

	public static Map<String, String> loadTableValues(List<Map<String, String>> map) {

        if (!map.isEmpty()) {
             return map.getFirst();
        }
        else {
            logger.error("List table is empty");
            System.out.println("List table is empty");
        }
        
        return new HashMap<>();
	}
	
	public static Map<String, String> loadTableValues(DataTable table) {
		
		List<Map<String, String>> dataList = table.asMaps(String.class, String.class);

        if (!dataList.isEmpty()) {
             return dataList.getFirst();
        }
        else {
            logger.error("Data table is empty");
            System.out.println("Data table is empty");
        }
        
        return new HashMap<>();
	}
	
	public static Map<String, String> updateTableWithGlobalValues(DataTable table) {
		
		Map<String, String> globalMap = getGlobalValues();

		Map<String, String> updatedInputValues = new HashMap<>();
		
        List<Map<String, String>> data = table.asMaps(String.class, String.class);
        for (Map<String, String> mainMap : data) {
            
        	for (Map.Entry<String, String> entry : mainMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                
                // Check if the value contains '$'
                if (value != null && value.contains("#")) {
                    
                	//String temp = String.valueOf(value);
                    Object updatedValue = globalMap.get(value.replace("#", ""));
                    
                    System.out.println(updatedValue);
                    // Update the value in the main map
                    if (updatedValue != null) {
                    	updatedInputValues.put(key, updatedValue.toString());
                    }
                    else {
                        logger.error("Global value has null");
                        System.out.println("Global value has null");
                    }
                }
                else
                	updatedInputValues.put(key, value);
            }
        }
        
        return updatedInputValues;
	}
}
