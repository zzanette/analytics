package com.agibank.analytics.service;

import com.agibank.analytics.config.ApplicationPropertiesConfig;
import com.agibank.analytics.domain.DataType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileParserService {

    private ApplicationPropertiesConfig propertiesConfig;

    public FileParserService(ApplicationPropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    public void writeFile(Path path, List<String> lines) {
        try {
            Files.write(path,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE_NEW);
        } catch (IOException ignored) {
        }
    }

    public Map<DataType, List<String[]>> parseFileToMap(Path file) {
        var map = new HashMap<DataType, List<String[]>>();
        try (var fileReader = new BufferedReader(new FileReader(file.toString()))) {
            var line = "";
            while ((line = fileReader.readLine()) != null) {
                parseSplitedLine(line.split(propertiesConfig.getColumnDelimiter()), map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private void parseSplitedLine(String[] splitedLine, HashMap<DataType, List<String[]>> map) {
        DataType type = DataType.getBy(splitedLine[0]);
        if (type == null) {
            throw new RuntimeException("Could not parse data type from file");
        }

        map.computeIfAbsent(type, k -> new ArrayList<>());
        map.get(type).add(splitedLine);
    }
}
