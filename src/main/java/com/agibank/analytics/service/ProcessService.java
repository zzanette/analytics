package com.agibank.analytics.service;

import com.agibank.analytics.domain.DataAnalytics;
import com.agibank.analytics.domain.DataType;
import com.agibank.analytics.domain.Salesman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ProcessService {

    private DataFileParserToMap dataFileParserToMap;

    @Autowired
    public ProcessService(DataFileParserToMap dataFileParserToMap) {
        this.dataFileParserToMap = dataFileParserToMap;
    }

    public void processAnalytics(Path file) {
        var map = dataFileParserToMap.parseFile(file);
        for (DataAnalytics salesman : map.get(DataType.SALESMAN)) {
            System.out.println("Meu deus: " + ((Salesman) salesman).getName());
        }
    }
}
