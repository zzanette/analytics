package com.agibank.analytics.schedule;

import com.agibank.analytics.config.ApplicationPropertiesConfig;
import com.agibank.analytics.service.ProcessService;
import com.agibank.analytics.service.ValidatePathFolderService;
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
  private ValidatePathFolderService pathFolderService;

  @Autowired
  public AnalyticsScheduler(ValidatePathFolderService pathFolderService,
      ProcessService processService, ApplicationPropertiesConfig propertiesConfig) {
    this.processService = processService;
    this.propertiesConfig = propertiesConfig;
    this.pathFolderService = pathFolderService;
  }

  @Scheduled(fixedRate = 1000)
  public void readFilesToAnalytics() {
    var homeInPath = Paths.get(propertiesConfig.getPathHomeIn());
    pathFolderService.validatePathFolder(homeInPath);

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
    pathFolderService.validatePathFolder(homeOutPath);

    try (var outPaths = Files.walk(homeOutPath)) {
      return outPaths
          .filter(Files::isRegularFile)
          .anyMatch(outFile -> outFile.getFileName().equals(inFile.getFileName()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return Boolean.FALSE;
  }
}
