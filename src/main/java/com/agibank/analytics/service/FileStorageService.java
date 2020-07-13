package com.agibank.analytics.service;

import com.agibank.analytics.config.ApplicationPropertiesConfig;
import com.agibank.analytics.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

  private ApplicationPropertiesConfig propertiesConfig;
  private ProcessService processService;

  @Autowired
  public FileStorageService(ApplicationPropertiesConfig propertiesConfig, ProcessService processService) {
    this.propertiesConfig = propertiesConfig;
    this.processService = processService;
  }

  public Resource loadFile(String fileName) {
    var file =
        new FileSystemResource(propertiesConfig.getPathHomeOut().concat("/").concat(fileName));
    if (!file.exists()) {
      throw new NotFoundException("File not found");
    }

    return file;
  }

  public void storeFile(MultipartFile file) {
    if (file == null) {
      throw new RuntimeException("You must select the a file for uploading");
    }
    var filePath = propertiesConfig.getPathHomeIn() + "/" + file.getOriginalFilename();
    try {
      Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
      processService.processAnalytics(Paths.get(filePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
