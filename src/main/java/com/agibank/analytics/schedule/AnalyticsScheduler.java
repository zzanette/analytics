package com.agibank.analytics.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class AnalyticsScheduler {

    @Value("${app.homepath}")
    private String homepath;

    @Scheduled(fixedRate = 1000)
    public void readFilesToAnalytics() {
        try (Stream<Path> paths = Files.walk(Paths.get(getPathHomeOut()))) {
            paths
                    .filter(Files::isRegularFile)
//                    .filter((path -> !isFileInPath(path.toString(), getPathHomeOut())))
                    .forEach(path -> System.out.println(path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Boolean isFileInPath(Path fileName, String path) {
        Paths.get()

        return false;
    }

    private String getPathHomeIn() {
        return homepath.concat("/data/in");
    }

    private String getPathHomeOut() {
        return homepath.concat("/data/out");
    }

}
