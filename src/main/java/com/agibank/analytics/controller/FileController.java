package com.agibank.analytics.controller;

import com.agibank.analytics.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/files")
public class FileController {

  private FileStorageService storageService;

  @Autowired
  public FileController(FileStorageService storageService) {
    this.storageService = storageService;
  }

  @RequestMapping(value = "/{filename:.+}", method = RequestMethod.GET)
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.loadFile(filename);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> postFile(@RequestParam("file") MultipartFile file) {
    storageService.storeFile(file);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
