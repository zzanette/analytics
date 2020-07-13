package com.agibank.analytics.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class ValidatePathFolderService {

  private static final String BAR = "/";

  public void validatePathFolder(Path path) {
    var file = new File(path.toString());
    if (!file.exists()) {
      file.mkdirs();
    }
  }
}
