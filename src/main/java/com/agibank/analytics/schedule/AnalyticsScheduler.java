package com.agibank.analytics.schedule;

import com.agibank.analytics.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Component
public class AnalyticsScheduler {

    @Value("${app.homepath}")
    private String homepath;

    private ProcessService processService;


    @Autowired
    public AnalyticsScheduler(ProcessService processService) {
        this.processService = processService;
    }

    @Scheduled(fixedRate = 1000)
    public void readFilesToAnalytics() {
        try (var inPaths = Files.walk(Paths.get(getPathHomeIn()))) {
            inPaths
                    .filter(Files::isRegularFile)
                    .filter((path -> !isInDataOutPath(path)))
                    .forEach(path -> CompletableFuture.runAsync(() -> processService.processAnalytics(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean isInDataOutPath(Path inFile) {
        try (var outPaths = Files.walk(Paths.get(getPathHomeOut()))) {
            return outPaths
                    .filter(Files::isRegularFile)
                    .anyMatch(outFile -> outFile.getFileName().equals(inFile.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Boolean.FALSE;
    }

    private String getPathHomeIn() {
        return homepath.concat("/data/in");
    }

    private String getPathHomeOut() {
        return homepath.concat("/data/out");
    }

}
