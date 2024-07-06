package com.Orange.Utilities;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.it.Ma;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

public class CommonMethods {

    public static Map<String, String> parseGivenInputTable(DataTable table) {

        Map<String, String> newTable = table.asMaps().getFirst();
        Map<String, String> dataTable = new HashMap<>();

        for (Map.Entry<String, String> entry : newTable.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if(value.contains("Password#")) {
                value = RandomStringUtils.randomAlphabetic(4, 6) + "aZ001";
            }
            dataTable.put(key, value);
        }
        return dataTable;
    }
}
