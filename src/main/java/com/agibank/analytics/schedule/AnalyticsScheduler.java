package com.agibank.analytics.schedule;

import com.agibank.analytics.config.ApplicationPropertiesConfig;
import com.agibank.analytics.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Component
public class AnalyticsScheduler {

  private ProcessService processService;
  private ApplicationPropertiesConfig propertiesConfig;

  @Autowired
  public AnalyticsScheduler(
      ProcessService processService, ApplicationPropertiesConfig propertiesConfig) {
    this.processService = processService;
    this.propertiesConfig = propertiesConfig;
  }

  @Scheduled(fixedRate = 1000)
  public void readFilesToAnalytics() {
    validatePathFolder(Paths.get(propertiesConfig.getPathHomtData()));

    var homeInPath = Paths.get(propertiesConfig.getPathHomeIn());
    validatePathFolder(homeInPath);

    try (var inPaths = Files.walk(homeInPath)) {
      inPaths
          .filter(Files::isRegularFile)
          .filter((path -> !isInDataOutPath(path)))
          .forEach(path -> CompletableFuture.runAsync(() -> processService.processAnalytics(path)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Boolean isInDataOutPath(Path inFile) {
    var homeOutPath = Paths.get(propertiesConfig.getPathHomeOut());
    validatePathFolder(homeOutPath);

    try (var outPaths = Files.walk(homeOutPath)) {
      return outPaths
          .filter(Files::isRegularFile)
          .anyMatch(outFile -> outFile.getFileName().equals(inFile.getFileName()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return Boolean.FALSE;
  }

  private void validatePathFolder(Path path) {
    var folder = new File(path.toString());
    if (!folder.exists()) {
      folder.mkdir();
    }
  }
}
