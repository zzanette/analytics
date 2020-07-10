package com.agibank.analytics.service;

import com.agibank.analytics.domain.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

@Service
public class DataFileParserToMap {

    private static final String COLUMN_DELIMITER = "ç";
    private static final String ITEM_DELIMITER = ",";
    private static final String PROPERTY_ITEM_DELIMITER = "-";

    public Map<DataType, List<DataAnalytics>> parseFile(Path file) {
        var map = initMap();

        try (var fileReader = new BufferedReader(new FileReader(file.toString()))) {
            var line = "";
            while ((line = fileReader.readLine()) != null) {
                parseSplitedLine(line.split(COLUMN_DELIMITER), map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private Map<DataType, List<DataAnalytics>> initMap() {
        var map = new HashMap<DataType, List<DataAnalytics>>();
        map.put(DataType.SALESMAN, new ArrayList<>());
        map.put(DataType.CUSTOMER, new ArrayList<>());
        map.put(DataType.SALE, new ArrayList<>());

        return map;
    }

    private void parseSplitedLine(String[] splitedData, Map<DataType, List<DataAnalytics>> map) {
        DataType type = DataType.getBy(splitedData[0]);
        switch (type) {
            case SALE:
                map.get(type).add(new Sale(Long.parseLong(splitedData[1]), parseItems(splitedData[2]), splitedData[3]));
                break;
            case CUSTOMER:
                map.get(type).add(new Customer(splitedData[1], splitedData[2], splitedData[3]));
                break;
            case SALESMAN:
                map.get(type).add(new Salesman(splitedData[1], splitedData[2], Float.valueOf(splitedData[3])));
                break;
            default:
                throw new RuntimeException("Could not parse data type from file");
        }
    }

    private List<ItemSale> parseItems(String fileItems) {
        fileItems = fileItems.replace("[", "").replace("]", "");
        var splitedItems = fileItems.split(ITEM_DELIMITER);
        var items = new ArrayList<ItemSale>();
        Arrays.stream(splitedItems).forEach((item) -> {
            var propertyItem = item.split(PROPERTY_ITEM_DELIMITER);
            items.add(new ItemSale(Long.parseLong(propertyItem[0]), Integer.parseInt(propertyItem[1]), Float.parseFloat(propertyItem[2])));
        });

        return items;
    }
}
