package com.agibank.analytics.service;

import com.agibank.analytics.config.ApplicationPropertiesConfig;
import com.agibank.analytics.domain.DataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProcessService {

  private CustomerDataParserService customerDataParserService;
  private SaleDataParserService saleDataParserService;
  private SalesmanDataParserService salesmanDataParserService;
  private GenerateAnalysisService generateAnalysisService;
  private FileParserService fileWriterService;
  private ApplicationPropertiesConfig propertiesConfig;

  @Autowired
  public ProcessService(
      CustomerDataParserService customerDataParserService,
      SaleDataParserService saleDataParserService,
      SalesmanDataParserService salesmanDataParserService,
      GenerateAnalysisService generateAnalysisService,
      FileParserService fileWriterService,
      ApplicationPropertiesConfig propertiesConfig) {
    this.customerDataParserService = customerDataParserService;
    this.saleDataParserService = saleDataParserService;
    this.salesmanDataParserService = salesmanDataParserService;
    this.generateAnalysisService = generateAnalysisService;
    this.fileWriterService = fileWriterService;
    this.propertiesConfig = propertiesConfig;
  }

  public void processAnalytics(Path file) {
    var map = fileWriterService.parseFileToMap(file);
    var lines =
        generateAnalysisService.generateLineAnalyses(
            customerDataParserService.parseFrom(map.get(DataType.CUSTOMER)),
            saleDataParserService.parseFrom(map.get(DataType.SALE)),
            salesmanDataParserService.parseFrom(map.get(DataType.SALESMAN)));
    fileWriterService.writeFile(
        Paths.get(
            propertiesConfig.getPathHomeOut().concat("/").concat(file.getFileName().toString())),
        lines);
  }
}
